package com.example.feature_transfer.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransferRequest(
    @SerialName("sourceCardId")
    val sourceCardId: String?=null,
    @SerialName("destinationCardId")
    val destinationCardId: String?=null,
    @SerialName("amount")
    val amount: String?=null,
    @SerialName("currency")
    val currency: String?=null
) {
}