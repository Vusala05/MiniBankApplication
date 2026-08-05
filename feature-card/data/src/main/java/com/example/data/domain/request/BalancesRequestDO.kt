package com.example.data.domain.request

import com.example.feature_card.data.request.BalancesRequest
import kotlinx.serialization.SerialName

data class BalancesRequestDO(
    val maskedPans: List<String>?=null
){
        companion object{
            fun BalancesRequestDO.toEntity(): BalancesRequest {
                return BalancesRequest(
                    maskedPans = this.maskedPans ?: emptyList()
                )
            }
        }
}