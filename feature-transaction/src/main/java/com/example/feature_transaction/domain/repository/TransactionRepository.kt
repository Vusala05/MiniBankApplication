package com.example.feature_transaction.domain.repository

import com.example.core.domain.model.ResultWrapper
import com.example.feature_transaction.domain.response.TransactionDO

interface TransactionRepository {
    suspend fun getTransaction () : ResultWrapper<List<TransactionDO>>
}