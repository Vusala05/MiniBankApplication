package com.example.data.data.util

import kotlinx.serialization.Serializable

@Serializable
enum class TransactionStatus {
    PENDING,
    COMPLETED,
    FAILED
}
