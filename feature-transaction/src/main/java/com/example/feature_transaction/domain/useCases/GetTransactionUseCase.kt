package com.example.feature_transaction.domain.useCases

import com.example.core.domain.model.ResultWrapper
import com.example.feature_transaction.domain.repository.TransactionRepository
import com.example.feature_transaction.domain.response.TransactionDO
import javax.inject.Inject

class GetTransactionUseCase @Inject constructor(
    val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(cardId : String?=null , offset : Int, userPullRequest : Boolean) : ResultWrapper<List<TransactionDO>>{
        return transactionRepository.getTransaction(cardId = cardId,offset = offset, userPullRequest =  userPullRequest)
    }
}