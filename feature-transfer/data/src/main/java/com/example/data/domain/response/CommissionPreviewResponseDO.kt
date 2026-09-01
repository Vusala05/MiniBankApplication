package com.example.data.domain.response

import kotlinx.serialization.SerialName

data class CommissionPreviewResponseDO(
    val amount: String,
    val commissionAmount: String ,
    val totalAmount: String ,
    val currency: String ,
    val commissionRate: String,
    val isLocal : Boolean,
    val requires3DS : Boolean
){

}