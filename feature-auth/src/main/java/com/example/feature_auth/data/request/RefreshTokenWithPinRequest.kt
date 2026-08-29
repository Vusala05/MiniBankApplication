package com.example.feature_auth.data.request

import com.example.feature_auth.domain.request.RefreshTokenWithPinRequestDO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenWithPinRequest(
    @SerialName("pin")
    val pin : String?=null
) {
    fun RefreshTokenWithPinRequestDO.toEntity() : RefreshTokenWithPinRequest{
       return RefreshTokenWithPinRequest(
           pin = this.pin
       )
    }
}