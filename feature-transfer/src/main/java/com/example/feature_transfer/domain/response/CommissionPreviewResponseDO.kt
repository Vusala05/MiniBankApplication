package com.example.feature_transfer.domain.response

data class CommissionPreviewResponseDO(
    val amount: String,
    val commissionAmount: String ,
    val totalAmount: String ,
    val currency: String ,
    val commissionRate: String
){

}