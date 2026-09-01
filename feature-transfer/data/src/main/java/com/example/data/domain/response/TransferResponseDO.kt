package com.example.data.domain.response

import com.example.feature_transaction.data.util.TransactionStatus


data class TransferResponseDO (
    val transactionId: String,
    val sourceCardId: String,
    val destinationPan: String,
    val transferredAmount: String,
    val commissionAmount: String,
    val totalAmount: String,
    val currency: String,
    val status: TransactionStatus,
    val timestamp: String,
    val requires3DS : Boolean,
    val threeDSUrl : String?,
    val callbackUrl : String?
){

}