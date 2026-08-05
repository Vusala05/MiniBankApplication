package com.example.feature_auth.data.helper

import com.example.core.data.interceptor.TokenInterceptor
import com.example.core.domain.feature.SessionTokenRefresher
import com.example.core.domain.model.ResultWrapper
import com.example.feature_auth.data.dataSource.AuthLocalDataSource
import com.example.feature_auth.domain.request.RefreshTokenRequestDO
import com.example.feature_auth.domain.useCases.GetAccessTokenUseCase
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionTokenRefreshImpl @Inject constructor(
    val authLocalDataSource: AuthLocalDataSource,
    val getAccessTokenUseCase: GetAccessTokenUseCase,
    val tokenInterceptor : TokenInterceptor
) : SessionTokenRefresher {

    private val mutex = Mutex()

    override suspend fun refreshIfPossible(): Boolean {
        val beforeWaitingRefreshToken = authLocalDataSource.getRefreshToken()
        if (beforeWaitingRefreshToken.isBlank()) return false

        return mutex.withLock {
            val updatedRefreshToken =  authLocalDataSource.getRefreshToken()
            if (updatedRefreshToken.isBlank()) return@withLock false

            val isRefreshTokenUpdated = beforeWaitingRefreshToken!= updatedRefreshToken
                    && !authLocalDataSource.getAccessToken().isBlank()

            if(isRefreshTokenUpdated){
                return@withLock true
            }


            val result = getAccessTokenUseCase(RefreshTokenRequestDO(
                refreshToken = authLocalDataSource.getRefreshToken()
            ))
            when(result){
                is ResultWrapper.Success ->{
                    val tokens = result.data
                    authLocalDataSource.saveRefreshToken(tokens.refreshToken ?:"")
                    authLocalDataSource.saveAccessToken(tokens.accessToken?:"")
                    tokenInterceptor.accessToken = tokens.accessToken ?: ""
                    true
                }
                is ResultWrapper.Error -> false
            }


        }
    }


}