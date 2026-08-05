package com.example.data.domain.response

import com.example.data.data.util.CardType
import com.example.feature_card.data.util.CardStatus



data class CardDO(
    val id: String,
    val maskedPan: String,         // e.g., "4111ABCD1234"
    val cardholderName: String,
    val cardType: CardType,
    val status: CardStatus,
    val expirationDate: String, // MM/YY
    val currency: String
) {
}