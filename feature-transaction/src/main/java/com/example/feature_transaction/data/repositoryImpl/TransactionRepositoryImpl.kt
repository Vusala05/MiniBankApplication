package com.example.feature_transaction.data.repositoryImpl

import com.example.core.data.network.apiCallingHandler
import com.example.core.domain.feature.GlobalNetwork
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.model.handleResultWrapper
import com.example.feature_transaction.data.dataSource.DataSource
import com.example.feature_transaction.domain.repository.TransactionRepository
import com.example.feature_transaction.domain.response.TransactionDO
import jakarta.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    val globalNetwork: GlobalNetwork,
    val dataSource: DataSource
) : TransactionRepository {
    override suspend fun getTransaction(): ResultWrapper<List<TransactionDO>> {
        return handleResultWrapper(result = apiCallingHandler(globalNetwork = globalNetwork){
            dataSource.getTransactions()
        }){ result ->
            result?.map{it.toDomain()} ?: emptyList()
        }
    }
}