package com.example.data.domain.repository

import com.example.core.domain.model.ResultWrapper
import com.example.data.domain.response.TransactionDO

interface TransactionRepository {
    suspend fun getTransaction (cardId : String?,offset : Int, userPullRequest : Boolean) : ResultWrapper<List<TransactionDO>>

}