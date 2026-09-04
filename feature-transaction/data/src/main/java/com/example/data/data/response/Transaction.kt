package com.example.data.data.response

import com.example.data.data.util.TransactionStatus
import com.example.data.data.util.TransactionType
import com.example.data.domain.response.TransactionDO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    @SerialName("id")
    val id: String?=null,
    @SerialName("cardId")
    val cardId: String?=null,
    @SerialName("amount")
    val amount: String?=null,
    @SerialName("currency")
    val currency: String?=null,
    @SerialName("type")
    val type: TransactionType,
    @SerialName("status")
    val status: TransactionStatus,
    @SerialName("merchantName")
    val merchantName: String? = null,
    @SerialName("timestamp")
    val timestamp: String?=null,        // ISO-8601 string
    @SerialName("commission")
    val commission: String?=null,
    @SerialName("destinationPan")
    val destinationPan : String?=null

){
    fun toDomain() : TransactionDO {
        return TransactionDO(
            id = id.orEmpty(),
            cardId = cardId.orEmpty(),
            amount = amount.orEmpty(),
            currency = currency.orEmpty(),
            type = type,
            status = status,
            merchantName = merchantName.orEmpty(),
            timestamp = timestamp.orEmpty(),
            commission = commission.orEmpty(),
            destinationPan = destinationPan
        )

    }

}