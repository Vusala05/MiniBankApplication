package com.example.feature_auth.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenWithPinResponse(
    @SerialName("accessToken")
    val accessToken: String?=null,
    @SerialName("refreshToken")
    val refreshToken: String?=null,
    @SerialName("expiresIn")
    val expiresIn: Long?=null, // Duration in seconds
    @SerialName("requiresPinSet")
    val requiresPinSet : Boolean?=null,
    @SerialName("isPinSet")
    val isPinSet : Boolean?=null

)


