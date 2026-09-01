package com.example.data.data.repositoryImpl

import com.example.core.data.module.CacheModule
import com.example.core.data.network.apiCallingHandler
import com.example.core.data.util.getAndConvertToModel
import com.example.core.data.util.writeAndConvertToJson
import com.example.core.domain.feature.CacheManager
import com.example.core.domain.feature.GlobalNetwork
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.model.handleResultWrapper
import com.example.data.domain.repository.TransactionRepository
import com.example.data.domain.response.TransactionDO
import com.example.feature_transaction.data.dataSource.DataSource
import jakarta.inject.Inject
import kotlin.time.Duration.Companion.minutes

class TransactionRepositoryImpl @Inject constructor(
    val globalNetwork: GlobalNetwork,
    val dataSource: DataSource,
    @CacheModule.LocalCacheManager val cacheManager: CacheManager
) : TransactionRepository {
    override suspend fun getTransaction(cardId : String?, offset : Int, userPullRequest : Boolean): ResultWrapper<List<TransactionDO>> {
        val transactionData = if(!userPullRequest) cacheManager.getAndConvertToModel< List<TransactionDO>>("${TRANSACTION_KEY}_${cardId}_$offset") else null
        if(transactionData!=null){
            return ResultWrapper.Success(data = transactionData)
        }
        return handleResultWrapper(result = apiCallingHandler(globalNetwork = globalNetwork){
            dataSource.getTransactions(cardId,offset = offset)
        }){ result ->
            result?.map{it.toDomain()}.orEmpty().also {
                if (userPullRequest){
                    cacheManager.invalidateGroupKey("${TRANSACTION_KEY}_$cardId")
                }
                cacheManager.writeAndConvertToJson(key = "${TRANSACTION_KEY}_${cardId}_$offset", groupKey = "${TRANSACTION_KEY}_$cardId" ,it,2.minutes.inWholeMilliseconds)
            }
        }
    }


    companion object{
        const val TRANSACTION_KEY = "TRANSACTION_KEY"
    }
}