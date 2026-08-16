package com.example.feature_transaction.data.util

import kotlinx.serialization.Serializable

@Serializable
enum class TransactionType {
    TRANSFER,
    PAYMENT,
    TOP_UP
}
