package com.example.feature_card.data.util

enum class CardStatus {
    ACTIVE,
    BLOCKED,
    EXPIRED;

    fun getCardStatus() : String = this.name
}
