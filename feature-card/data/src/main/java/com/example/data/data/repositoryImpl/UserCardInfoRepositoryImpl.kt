package com.example.feature_card.data.repositoryImpl

import com.example.core.data.module.CacheModule
import com.example.core.data.network.apiCallingHandler
import com.example.core.data.util.getAndConvertToModel
import com.example.core.data.util.writeAndConvertToJson
import com.example.core.domain.feature.CacheManager
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
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes
class UserCardInfoRepositoryImpl @Inject constructor(
    private val dataSource: DataSource,
    private val globalNetwork: GlobalNetwork,
   @CacheModule.InMemoryCacheManager private val cacheManager: CacheManager
) : UserCardInfoRepository {
    val mutex = Mutex()


    override suspend fun getCards(userForceToRefresh : Boolean): ResultWrapper<List<CardDO>> {

        val cachedCardList = cacheManager.getAndConvertToModel<List<CardDO>>(CARD_CACHE_KEY, userForceToRefresh)


        if(cachedCardList!=null){
            return ResultWrapper.Success(data = cachedCardList)
        }
        mutex.withLock {
            return handleResultWrapper(result = apiCallingHandler(globalNetwork = globalNetwork){
                dataSource.getCards()
            }){ result ->
                result?.map { it.toDomain() }.orEmpty().also { cacheManager.writeAndConvertToJson(CARD_CACHE_KEY, it,2.minutes.inWholeMilliseconds) }
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

    companion object{
        const val CARD_CACHE_KEY = "Card_Cache_Key"
    }
}
