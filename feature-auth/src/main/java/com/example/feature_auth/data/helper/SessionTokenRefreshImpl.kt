package com.example.feature_auth.data.helper

import androidx.compose.ui.node.Ref
import com.example.core.data.interceptor.TokenInterceptor
import com.example.core.domain.feature.RefreshedResult
import com.example.core.domain.feature.SessionTokenRefresher
import com.example.core.domain.model.ResultWrapper
import com.example.feature_auth.data.dataSource.AuthLocalDataSource
import com.example.feature_auth.domain.request.RefreshTokenRequestDO
import com.example.feature_auth.domain.useCases.GetAccessTokenUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionTokenRefreshImpl @Inject constructor(
    val authLocalDataSource: AuthLocalDataSource,
    val getAccessTokenUseCase: GetAccessTokenUseCase,
    val tokenInterceptor: TokenInterceptor
) : SessionTokenRefresher {

    override suspend fun refreshIfPossible(): RefreshedResult {
        val result = getAccessTokenUseCase(
            RefreshTokenRequestDO(
                refreshToken = authLocalDataSource.getRefreshToken()
            )
        )
        return when (result) {
            is ResultWrapper.Success -> {
                val tokens = result.data
                authLocalDataSource.saveRefreshToken(tokens.refreshToken)
                authLocalDataSource.saveAccessToken(tokens.accessToken)
                tokenInterceptor.accessToken = tokens.accessToken
                RefreshedResult.Success(requiredPinSet = tokens.requiresPinSet ?:false)
            }

            is ResultWrapper.Error -> RefreshedResult.Failed
        }
    }


}