package com.example.feature_card.data.response

import com.example.data.data.util.CardType
import com.example.data.domain.response.CardDO
import com.example.feature_card.data.util.CardStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Card(
    @SerialName("id")
    val id: String?=null,
    @SerialName("maskedPan")
    val maskedPan: String?=null,         // e.g., "4111ABCD1234"
    @SerialName("cardholderName")
    val cardholderName: String?=null,
    @SerialName("cardType")
    val cardType: CardType,
    @SerialName("status")
    val status: CardStatus,
    @SerialName("expirationDate")
    val expirationDate: String?=null, // MM/YY
    @SerialName("currency")
    val currency: String?=null           // e.g., "AZN", "USD"
){
    companion object{

        fun Card.toDomain(): CardDO {
            return CardDO(
                id = this.id.orEmpty(),
                maskedPan = this.maskedPan.orEmpty(),
                cardholderName = this.cardholderName.orEmpty(),
                cardType = this.cardType,
                status = this.status,
                expirationDate = this.expirationDate.orEmpty(),
                currency = this.currency.orEmpty()
            )
        }
    }
}