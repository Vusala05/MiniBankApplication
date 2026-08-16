package com.example.feature_transaction.domain.response

import com.example.feature_transaction.data.util.TransactionStatus
import com.example.feature_transaction.data.util.TransactionType
import kotlinx.serialization.SerialName
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
    val commission: String
) {
}