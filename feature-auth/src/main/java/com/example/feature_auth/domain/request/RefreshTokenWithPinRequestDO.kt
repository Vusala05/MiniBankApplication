package com.example.feature_auth.domain.request

import com.example.feature_auth.data.request.RefreshTokenWithPinRequest

data class RefreshTokenWithPinRequestDO(
    val pin : String
) {
    fun toEntity() : RefreshTokenWithPinRequest{
        return RefreshTokenWithPinRequest(
            pin = this.pin
        )
    }
}