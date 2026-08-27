package com.example.data.domain.response

import com.example.data.data.util.CardType
import com.example.feature_card.data.util.CardStatus
import kotlinx.serialization.Serializable


@Serializable
data class CardDO(
    val id: String,
    val maskedPan: String,
    val cardholderName: String,
    val cardType: CardType,
    val status: CardStatus,
    val expirationDate: String,
    val currency: String
) {
    companion object{
        val emptyData = CardDO(
            id = "",
            maskedPan = "",
            cardholderName = "",
            cardType = CardType.DEBIT,
            status = CardStatus.EXPIRED,
            expirationDate = "",
            currency = ""
        )
    }
}