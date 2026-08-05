package com.example.feature_card.data.repositoryImpl

import com.example.core.data.network.apiCallingHandler
import com.example.core.domain.feature.GlobalNetwork
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.model.handleResultWrapper
import com.example.data.domain.repository.UserCardInfoRepository
import com.example.data.domain.request.BalancesRequestDO
import com.example.data.domain.request.BalancesRequestDO.Companion.toEntity
import com.example.data.domain.response.BalanceDO
import com.example.data.domain.response.CardDO
import com.example.feature_card.data.dataSource.DataSource
import com.example.feature_card.data.response.Balance.Companion.toDomain
import com.example.feature_card.data.response.Card.Companion.toDomain
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.ConcurrentHashMap

import javax.inject.Inject
import javax.inject.Qualifier
import kotlin.time.Duration.Companion.minutes

interface CacheManager {
    fun write(key: String, value: Any, expirationMillis: Long = 2.minutes.inWholeMilliseconds)
    fun read(key: String) : Any?
}

@Qualifier
annotation class InMemoryCacheManagerQualifier

class InMemoryCacheManager : CacheManager {
    private val cachedEntries = ConcurrentHashMap<String, CacheEntry>()
    class CacheEntry (
        val value: Any,
        val timestampNanos: Long,
        val expirationMillis: Long
    )

    // TODO

    override fun write(key: String, value: Any, expirationMillis: Long) {
        cachedEntries[key] = CacheEntry(value, System.nanoTime(), expirationMillis)
    }

    override fun read(key: String): Any? {
        val matchingEntry = cachedEntries[key] ?: return null

        val isExpired = System.nanoTime() - matchingEntry.timestampNanos >= matchingEntry.expirationMillis * 1000L
        if (isExpired) return null

        return matchingEntry
    }

}

//class CacheManager {
//
//
//
//    fun write(data: Map<String, Any>, expirationMillis: Long) {
//
//    }
//
//}

class UserCardInfoRepositoryImpl @Inject constructor(
    val dataSource: DataSource,
    val globalNetwork: GlobalNetwork,
    @InMemoryCacheManagerQualifier
    val inMemoryCacheManager: CacheManager
) : UserCardInfoRepository {
    val mutex = Mutex()
    companion object {
        const val CARD_LIST_KEY = "cardListKey"
    }


    override suspend fun getCards(): ResultWrapper<List<CardDO>> {

        val cachedEntry = inMemoryCacheManager.read(CARD_LIST_KEY) as List<CardDO>?
        if (cachedEntry != null) {
            return ResultWrapper.Success(data = cachedEntry)
        }

        mutex.withLock {
            return handleResultWrapper(result = apiCallingHandler(globalNetwork = globalNetwork){
                dataSource.getCards()
            }){ result ->
                result?.map { it.toDomain() }.orEmpty().also { inMemoryCacheManager.write(CARD_LIST_KEY, it) }
            }
        }


    }

    override suspend fun getBalance(balancesRequestDO: BalancesRequestDO): ResultWrapper<List<BalanceDO>> {
        return handleResultWrapper(result = apiCallingHandler(globalNetwork = globalNetwork){
            dataSource.getBalances(request = balancesRequestDO.toEntity())
        }){ result ->
            result?.map { it.toDomain() } ?: emptyList()
        }
    }
}