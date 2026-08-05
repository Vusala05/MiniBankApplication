package com.example.feature_transfer.data.response

import com.example.feature_transfer.domain.response.CommissionPreviewResponseDO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommissionPreviewResponse(
    @SerialName("amount")
    val amount: String?=null,
    @SerialName("commissionAmount")
    val commissionAmount: String?=null,
    @SerialName("totalAmount")
    val totalAmount: String?=null,
    @SerialName("currency")
    val currency: String?=null,
    @SerialName("commissionRate")
    val commissionRate: String?=null
) {
    fun toDomain() : CommissionPreviewResponseDO{
        return CommissionPreviewResponseDO(
            amount = this.amount.orEmpty(),
            commissionAmount = this.commissionAmount.orEmpty(),
            totalAmount = this.totalAmount.orEmpty(),
            currency = this.currency.orEmpty(),
            commissionRate = this.commissionRate.orEmpty()
        )
    }
}