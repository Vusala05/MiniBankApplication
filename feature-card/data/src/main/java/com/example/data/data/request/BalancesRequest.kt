package com.example.feature_card.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BalancesRequest(
    @SerialName("maskedPans")
    val maskedPans: List<String>
){

}