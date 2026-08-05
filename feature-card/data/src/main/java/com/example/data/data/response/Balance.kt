package com.example.feature_card.data.response

import com.example.data.domain.response.BalanceDO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Balance(
    @SerialName("maskedPan")
    val maskedPan: String?=null,
    @SerialName("amount")
    val amount: String?=null,              // Decimal representation e.g. "150.50"
    @SerialName("currency")
    val currency: String?=null
){
    companion object{
        fun Balance.toDomain(): BalanceDO {
            return BalanceDO(
                maskedPan = this.maskedPan.orEmpty(),
                amount = this.amount.orEmpty(),
                currency = this.currency.orEmpty()
            )
        }
    }
}