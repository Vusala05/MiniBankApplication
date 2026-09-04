package com.example.feature_auth.domain.repository

import com.example.core.domain.model.ResultWrapper
import com.example.feature_auth.domain.request.RefreshTokenRequestDO
import com.example.feature_auth.domain.request.RefreshTokenWithPinRequestDO
import com.example.feature_auth.domain.response.TokenResponseDO

interface UserAuthRepository{
    suspend fun getAccessToken(refreshTokenRequestDO: RefreshTokenRequestDO) : ResultWrapper<TokenResponseDO>
    suspend fun getTokenWithPin(getTokenWithPinRequestDO: RefreshTokenWithPinRequestDO) : ResultWrapper<TokenResponseDO>

}
