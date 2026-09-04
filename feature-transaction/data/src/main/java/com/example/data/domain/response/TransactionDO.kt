package com.example.data.domain.response

import com.example.data.data.util.TransactionStatus
import com.example.data.data.util.TransactionType
import kotlinx.serialization.Serializable

@Serializable
data class TransactionDO(
    val id: String,
    val cardId: String,
    val amount: String,
    val currency: String,
    val type: TransactionType,
    val status: TransactionStatus,
    val merchantName: String? = null,
    val timestamp: String,        // ISO-8601 string
    val commission: String,
    val destinationPan : String?=null
) {
}