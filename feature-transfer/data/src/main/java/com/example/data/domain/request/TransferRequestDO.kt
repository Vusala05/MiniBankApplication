package com.example.data.domain.request

import com.example.data.data.request.TransferRequest

data class TransferRequestDO(
    val sourceCardId: String = "",
    val destinationCardId: String?=null,
    val destinationPan : String?=null,
    val amount: String = "",
    val currency: String = ""
) {
    fun toEntity() : TransferRequest {
        return TransferRequest(
            sourceCardId = this.sourceCardId,
            destinationCardId = this.destinationCardId,
            destinationPan = this.destinationPan,
            amount = this.amount,
            currency = this.currency
        )
    }
}