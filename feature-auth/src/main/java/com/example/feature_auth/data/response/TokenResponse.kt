package com.example.feature_auth.data.response

import com.example.feature_auth.domain.response.TokenResponseDO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    @SerialName("accessToken")
    val accessToken: String?=null,
    @SerialName("refreshToken")
    val refreshToken: String?=null,
    @SerialName("expiresIn")
    val expiresIn: Long?=null // Duration in seconds
){
        fun toDomain(): TokenResponseDO {
            return TokenResponseDO(
                accessToken = this.accessToken.orEmpty(),
                refreshToken = this.refreshToken.orEmpty(),
                expiresIn = this.expiresIn ?: 0L
            )
        }



}