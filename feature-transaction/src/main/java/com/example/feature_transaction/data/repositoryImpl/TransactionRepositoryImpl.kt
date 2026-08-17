package com.example.feature_transaction.data.repositoryImpl

import com.example.core.data.module.CacheModule
import com.example.core.data.network.apiCallingHandler
import com.example.core.data.util.getAndConvertToModel
import com.example.core.data.util.writeAndConvertToJson
import com.example.core.domain.feature.CacheManager
import com.example.core.domain.feature.GlobalNetwork
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.model.handleResultWrapper
import com.example.feature_transaction.data.dataSource.DataSource
import com.example.feature_transaction.domain.repository.TransactionRepository
import com.example.feature_transaction.domain.response.TransactionDO
import jakarta.inject.Inject
import kotlin.time.Duration.Companion.minutes

class TransactionRepositoryImpl @Inject constructor(
    val globalNetwork: GlobalNetwork,
    val dataSource: DataSource,
    @CacheModule.LocalCacheManager val cacheManager: CacheManager
) : TransactionRepository {
    override suspend fun getTransaction(offset : Int, userPullRequest : Boolean): ResultWrapper<List<TransactionDO>> {
        val transactionData = cacheManager.getAndConvertToModel< List<TransactionDO>>("${TRANSACTION_KEY}_$offset",userPullRequest)
        if(transactionData!=null){
            return ResultWrapper.Success(data = transactionData)
        }
        return handleResultWrapper(result = apiCallingHandler(globalNetwork = globalNetwork){
            dataSource.getTransactions(offset = offset)
        }){ result ->
            result?.map{it.toDomain()}.orEmpty().also { cacheManager.writeAndConvertToJson("${TRANSACTION_KEY}_$offset",it,2.minutes.inWholeMilliseconds) }
        }
    }


    companion object{
        const val TRANSACTION_KEY = "TRANSACTION_KEY"
    }
}