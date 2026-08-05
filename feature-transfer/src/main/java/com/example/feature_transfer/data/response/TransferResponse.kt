package com.example.feature_transfer.data.response

import com.example.feature_transfer.data.util.TransactionStatus
import com.example.feature_transfer.domain.response.TransferResponseDO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransferResponse(
    @SerialName("transactionId")
    val transactionId: String?=null,
    @SerialName("sourceCardId")
    val sourceCardId: String?=null,
    @SerialName("destinationCardId")
    val destinationCardId: String?=null,
    @SerialName("transferredAmount")
    val transferredAmount: String?=null,
    @SerialName("commissionAmount")
    val commissionAmount: String?=null,
    @SerialName("totalAmount")
    val totalAmount: String?=null,
    @SerialName("currency")
    val currency: String?=null,
    @SerialName("status")
    val status: TransactionStatus?=null,
    @SerialName("timestamp")
    val timestamp: String?=null
) {
        fun toDomain(): TransferResponseDO {
            return TransferResponseDO(
                transactionId = this.transactionId.orEmpty(),
                sourceCardId = this.sourceCardId.orEmpty(),
                destinationCardId = this.destinationCardId.orEmpty(),
                transferredAmount = this.transferredAmount.orEmpty(),
                commissionAmount = this.commissionAmount.orEmpty(),
                totalAmount = this.totalAmount.orEmpty(),
                currency = this.currency.orEmpty(),
                status = this.status ?: TransactionStatus.UNKNOWN,
                timestamp = this.timestamp.orEmpty() )
        }
}

