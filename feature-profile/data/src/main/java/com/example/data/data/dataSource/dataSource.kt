package com.example.feature_profile.data.dataSource

import com.example.core.data.model.BaseResponse
import com.example.data.data.request.UpdateUserProfileRequest
import com.example.data.data.response.UserProfile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface DataSource {

    @GET("user/profile")
    suspend fun getUserProfile(): Response<BaseResponse<UserProfile>>


    @PUT("user/profile")
    suspend fun updateUserProfile(
        @Body request: UpdateUserProfileRequest
    ): Response<BaseResponse<UserProfile>>

}
