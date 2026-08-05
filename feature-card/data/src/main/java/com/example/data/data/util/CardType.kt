package com.example.data.data.util

enum class CardType {
    DEBIT,
    VIRTUAL;

    fun getCardType(): String = this.name

}
