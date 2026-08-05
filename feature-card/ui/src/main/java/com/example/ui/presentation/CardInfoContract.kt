package com.example.ui.presentation

import com.example.data.domain.response.CardDO
import com.example.data.domain.response.CardWithBalanceDO
import com.example.navigation.Route

object CardInfoContract {

    sealed interface Effect {
        data class ShowErrorMessage(val message : Int) : Effect
    }

    sealed interface Intent{
        data class OnCardChange(val index : Int) : Intent
        data class OnNavigateTransaction(val route: Route) : Intent
        data class OnNavigateBalanceTransfer(val route: Route) : Intent
    }

    data class State(
        val isCardSectionLoading : Boolean = false,
        val isBalanceSectionLoading : Boolean = false,
        val cardIndex : Int = 0,
        val cardList : List<CardDO> = emptyList(),
        val cardWithBalanceList : List<CardWithBalanceDO> = emptyList()

    )
}