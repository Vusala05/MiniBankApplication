package com.example.feature_auth.domain.repository

import com.example.core.domain.model.ResultWrapper
import com.example.feature_auth.data.dataSource.DataSource
import com.example.feature_auth.domain.request.RefreshTokenRequestDO
import com.example.feature_auth.domain.request.UpdateUserProfileRequestDO
import com.example.feature_auth.domain.response.TokenResponseDO
import com.example.feature_auth.domain.response.UserProfileDO

interface UserAuthRepository{
    suspend fun getAccessToken(refreshTokenRequestDO: RefreshTokenRequestDO) : ResultWrapper<TokenResponseDO>
    suspend fun updateUserProfileProfile(updateUserProfileRequestDO: UpdateUserProfileRequestDO) : ResultWrapper<UserProfileDO>
    suspend fun getUseProfile() : ResultWrapper<UserProfileDO>

}
