package com.example.feature_auth.domain.response

data class TokenResponseDO(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long
){
    fun emptyMock() : TokenResponseDO {
        return TokenResponseDO(
            accessToken = "",
            refreshToken = "",
            expiresIn = 0L
        )

    }
}
