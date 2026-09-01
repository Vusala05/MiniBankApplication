package com.example.data.data.util

import kotlinx.serialization.Serializable

@Serializable
enum class TransactionType {
    TRANSFER,
    PAYMENT,
    TOP_UP
}
