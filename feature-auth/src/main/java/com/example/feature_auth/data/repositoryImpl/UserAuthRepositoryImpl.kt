package com.example.feature_auth.data.repositoryImpl

import com.example.core.data.network.apiCallingHandler
import com.example.core.domain.feature.GlobalNetwork
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.model.handleResultWrapper
import com.example.feature_auth.data.dataSource.DataSource
import com.example.feature_auth.data.request.RefreshTokenRequest.Companion.toEntity
import com.example.feature_auth.data.request.UpdateUserProfileRequest.Companion.toEntity
import com.example.feature_auth.data.response.UserProfile.Companion.toDomain
import com.example.feature_auth.domain.repository.UserAuthRepository
import com.example.feature_auth.domain.request.RefreshTokenRequestDO
import com.example.feature_auth.domain.request.UpdateUserProfileRequestDO
import com.example.feature_auth.domain.response.TokenResponseDO
import com.example.feature_auth.domain.response.UserProfileDO
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(
    val dataSource: DataSource,
    val globalNetwork: GlobalNetwork
) : UserAuthRepository {
    override suspend fun getAccessToken(refreshTokenRequestDO: RefreshTokenRequestDO): ResultWrapper<TokenResponseDO> {
     return handleResultWrapper(result = apiCallingHandler(globalNetwork = globalNetwork){
         dataSource.refreshToken(request = refreshTokenRequestDO.toEntity())
     }){ result ->
       result?.toDomain() ?: TokenResponseDO("","",0L)
     }
    }

    override suspend fun updateUserProfileProfile(updateUserProfileRequestDO: UpdateUserProfileRequestDO): ResultWrapper<UserProfileDO> {
        return handleResultWrapper(result = apiCallingHandler(globalNetwork = globalNetwork){
            dataSource.updateUserProfile(request = updateUserProfileRequestDO.toEntity())
        }){ result ->
            result?.toDomain() ?: UserProfileDO("","","","","","")
        }
    }

    override suspend fun getUseProfile(): ResultWrapper<UserProfileDO> {
        return handleResultWrapper(result = apiCallingHandler(globalNetwork = globalNetwork){
            dataSource.getUserProfile()
        }){ result ->
            result?.toDomain() ?: UserProfileDO("","","","","","")
        }
    }


}