package com.example.navigation

sealed class DeeplinkNavigator(val routeLink : String) {
    data object CardsInfo : DeeplinkNavigator(CARDS_URL)
    data object Transaction : DeeplinkNavigator(TRANSACTIONS_URL)
    data object Transfer : DeeplinkNavigator(TRANSFER_URL)


    companion object {
        const val CARDS_URL = "minibank.az://cardsandbalances"
        const val TRANSACTIONS_URL = "minibank.az://transactions"
        const val TRANSFER_URL = "minibank.az://transfer"
    }
}