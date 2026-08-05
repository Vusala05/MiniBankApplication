package com.example.feature_auth.data.request

import com.example.feature_auth.domain.request.RefreshTokenRequestDO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    @SerialName("refreshToken")
    val refreshToken: String?=null
){
    companion object{
        fun RefreshTokenRequestDO.toEntity() : RefreshTokenRequest{
            return RefreshTokenRequest(
                refreshToken = this.refreshToken
            )
        }
    }

}