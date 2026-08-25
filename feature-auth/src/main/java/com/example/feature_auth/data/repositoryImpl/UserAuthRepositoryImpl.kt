package com.example.feature_auth.data.repositoryImpl

import com.example.core.data.network.apiCallingHandler
import com.example.core.domain.feature.GlobalNetwork
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.model.handleResultWrapper
import com.example.feature_auth.data.dataSource.DataSource
import com.example.feature_auth.data.request.RefreshTokenRequest.Companion.toEntity
import com.example.feature_auth.domain.repository.UserAuthRepository
import com.example.feature_auth.domain.request.RefreshTokenRequestDO
import com.example.feature_auth.domain.response.TokenResponseDO
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(
    val dataSource: DataSource,
    val globalNetwork: GlobalNetwork
) : UserAuthRepository {
    override suspend fun getAccessToken(refreshTokenRequestDO: RefreshTokenRequestDO): ResultWrapper<TokenResponseDO> {
     return handleResultWrapper(result = apiCallingHandler(globalNetwork = globalNetwork){
         dataSource.refreshToken(request = refreshTokenRequestDO.toEntity())
     }){ result ->
       result?.toDomain() ?: TokenResponseDO("","",0L, requiresPinSet = false, isPinSet = false)
     }
    }
}