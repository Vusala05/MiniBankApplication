package com.example.feature_card.data.repositoryImpl

import com.example.core.data.network.apiCallingHandler
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
import java.util.concurrent.ConcurrentHashMap

import javax.inject.Inject
import javax.inject.Qualifier
import kotlin.time.Duration.Companion.minutes
class UserCardInfoRepositoryImpl @Inject constructor(
    val dataSource: DataSource,
    val globalNetwork: GlobalNetwork,
    val cacheManager: CacheManager
) : UserCardInfoRepository {
    val mutex = Mutex()


    override suspend fun getCards(userForceToRefresh : Boolean): ResultWrapper<List<CardDO>> {

        val cachedCardList = cacheManager.getData(CARD_CASH_KEY, userForceToRefresh) as? List<CardDO>


        if(cachedCardList!=null){
            return ResultWrapper.Success(data = cachedCardList)
        }
        mutex.withLock {
            return handleResultWrapper(result = apiCallingHandler(globalNetwork = globalNetwork){
                dataSource.getCards()
            }){ result ->
                result?.map { it.toDomain() }.orEmpty().also { cacheManager.writeData(CARD_CASH_KEY, it,2.minutes.inWholeMilliseconds) }
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
        const val CARD_CASH_KEY = "Card_Cash_Key"
        const val BALANCE_CASH_KEY = "Balance_Cash_Key"
    }
}
