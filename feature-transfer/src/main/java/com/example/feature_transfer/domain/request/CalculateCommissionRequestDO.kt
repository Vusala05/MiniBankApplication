package com.example.feature_transfer.domain.request

import com.example.feature_transfer.data.request.CalculateCommissionRequest

data class CalculateCommissionRequestDO(
    val sourceCardId: String = "",
    val destinationCardId: String?=null ,
    val destinationPan : String?=null,
    val amount: String = "" ,
    val currency: String = ""
){
    fun toEntity() : CalculateCommissionRequest{
        return CalculateCommissionRequest(
            sourceCardId = this.sourceCardId,
            destinationCardId = this.destinationCardId,
            destinationPan = this.destinationPan,
            amount = this.amount,
            currency = this.currency
        )
    }
}