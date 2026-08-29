package com.example.feature_auth.domain.useCases

import com.example.core.domain.model.ResultWrapper
import com.example.feature_auth.domain.repository.UserAuthRepository
import com.example.feature_auth.domain.request.RefreshTokenRequestDO
import com.example.feature_auth.domain.response.TokenResponseDO
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    val authRepository: UserAuthRepository
) {
    suspend operator fun invoke(refreshTokenRequestDO: RefreshTokenRequestDO) : ResultWrapper<TokenResponseDO>{
        return authRepository.getAccessToken(refreshTokenRequestDO)
    }
}