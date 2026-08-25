package com.example.data.domain.repository

import com.example.core.data.network.apiCallingHandler
import com.example.core.domain.feature.GlobalNetwork
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.model.handleResultWrapper
import com.example.data.data.dataSource.DataSource
import com.example.data.data.repositoryImpl.ProfileRepository
import com.example.data.data.request.UpdateUserProfileRequest.Companion.toEntity
import com.example.data.data.response.UserProfile.Companion.toDomain
import com.example.data.domain.request.UpdateUserProfileRequestDO
import com.example.data.domain.response.UserProfileDO
import jakarta.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    val dataSource: DataSource,
    val globalNetwork: GlobalNetwork
) : ProfileRepository {
    override suspend fun updateUserProfileProfile(updateUserProfileRequestDO: UpdateUserProfileRequestDO): ResultWrapper<UserProfileDO> {
        return handleResultWrapper(result = apiCallingHandler(globalNetwork = globalNetwork){
            dataSource.updateUserProfile(request = updateUserProfileRequestDO.toEntity())
        }){ result ->
            result?.toDomain() ?: UserProfileDO("","","","","","",false)
        }
    }

    override suspend fun getUseProfile(): ResultWrapper<UserProfileDO> {
        return handleResultWrapper(result = apiCallingHandler(globalNetwork = globalNetwork){
            dataSource.getUserProfile()
        }){ result ->
            result?.toDomain() ?: UserProfileDO("","","","","","", hasPin = false)
        }
    }
}