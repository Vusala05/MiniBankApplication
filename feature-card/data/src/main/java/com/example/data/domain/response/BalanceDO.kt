package com.example.data.domain.response


data class BalanceDO(
    val maskedPan: String,
    val amount: String,              // Decimal representation e.g. "150.50"
    val currency: String
) {
}