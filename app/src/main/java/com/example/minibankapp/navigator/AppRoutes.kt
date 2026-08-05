package com.example.minibankapp.navigator

import kotlinx.serialization.Serializable

sealed interface AppRoutes {

    @Serializable
    data object CardInfo : AppRoutes

    @Serializable
    data object Transactions : AppRoutes

    @Serializable
    data object Transfer : AppRoutes

    @Serializable
    data object UserInfo : AppRoutes
}