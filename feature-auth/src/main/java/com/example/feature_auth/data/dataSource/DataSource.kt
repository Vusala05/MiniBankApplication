package com.example.feature_auth.data.dataSource

import com.example.core.data.model.BaseResponse
import com.example.feature_auth.data.request.RefreshTokenRequest
import com.example.feature_auth.data.request.UpdateUserProfileRequest
import com.example.feature_auth.data.response.TokenResponse
import com.example.feature_auth.data.response.UserProfile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface DataSource {

    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Response<BaseResponse<TokenResponse>>

    @GET("user/profile")
    suspend fun getUserProfile(): Response<BaseResponse<UserProfile>>


    @PUT("user/profile")
    suspend fun updateUserProfile(
        @Body request: UpdateUserProfileRequest
    ): Response<BaseResponse<UserProfile>>

}

