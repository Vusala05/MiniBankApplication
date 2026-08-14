package com.example.feature_transaction.ui.presentation

import com.example.feature_transaction.domain.response.TransactionDO

object TransactionContract {

    sealed interface Effect {
        data class ShowErrorMessage(val message : Int) : Effect
    }

    sealed interface Intent{
        data object LoadNextPage : Intent
        data object ReloadPage : Intent
    }

    data class State(
        val isLoading : Boolean = false,
        val transactionList : List<TransactionDO> = emptyList(),
        val isAppendLoading : Boolean = false,
        val paginationIsFinished : Boolean = false,
        val transactionWithDay  : Map<String, List<TransactionDO>> = emptyMap()
    )
    const val MAX_PAGE = 20

}
data class GroupedTransactionList(
    val time : String,
    val transactionList : List<TransactionDO>
)