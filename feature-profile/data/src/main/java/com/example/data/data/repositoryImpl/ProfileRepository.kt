package com.example.data.data.repositoryImpl

import com.example.core.domain.model.ResultWrapper
import com.example.data.domain.request.UpdateUserProfileRequestDO
import com.example.data.domain.response.UserProfileDO

interface ProfileRepository {

    suspend fun updateUserProfileProfile(updateUserProfileRequestDO: UpdateUserProfileRequestDO) : ResultWrapper<UserProfileDO>
    suspend fun getUseProfile() : ResultWrapper<UserProfileDO>
}