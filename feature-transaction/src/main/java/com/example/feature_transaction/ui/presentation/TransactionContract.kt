package com.example.feature_transaction.ui.presentation

import com.example.feature_transaction.domain.response.TransactionDO

object TransactionContract {

    sealed interface Effect {
        data class ShowErrorMessage(val message : Int) : Effect
    }

    sealed interface Intent{

    }

    data class State(
        val isLoading : Boolean = false,
        val transactionList : List<TransactionDO> = emptyList()
    )
}