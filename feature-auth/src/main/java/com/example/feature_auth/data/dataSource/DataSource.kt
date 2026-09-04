package com.example.feature_auth.data.dataSource

import com.example.core.data.model.BaseResponse
import com.example.feature_auth.data.request.RefreshTokenRequest
import com.example.feature_auth.data.request.RefreshTokenWithPinRequest
import com.example.feature_auth.data.response.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DataSource {

    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Response<BaseResponse<TokenResponse>>


    @POST("auth/refresh-pin")
    suspend fun refreshTokenWithPin(
        @Body request: RefreshTokenWithPinRequest
    ): Response<BaseResponse<TokenResponse>>





}

