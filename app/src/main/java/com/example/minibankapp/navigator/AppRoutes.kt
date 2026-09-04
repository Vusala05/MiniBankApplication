package com.example.minibankapp.navigator

import kotlinx.serialization.Serializable

sealed interface AppRoutes {

    @Serializable
    data object CardInfo : AppRoutes

    @Serializable
    data class Transactions(val cardId : String?=null) : AppRoutes

    @Serializable
    data object Transfer : AppRoutes

    @Serializable
    data object UserProfile : AppRoutes

    @Serializable
    data object UserAuth : AppRoutes
}