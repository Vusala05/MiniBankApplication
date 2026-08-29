package com.example.feature_auth.domain.useCases

import com.example.core.domain.model.ResultWrapper
import com.example.feature_auth.domain.repository.UserAuthRepository
import com.example.feature_auth.domain.request.RefreshTokenWithPinRequestDO
import com.example.feature_auth.domain.response.TokenResponseDO
import javax.inject.Inject

class GetTokenWithPinUseCase @Inject constructor(
    val authRepository: UserAuthRepository
) {
    suspend operator fun invoke(getTokenWithPinRequestDO: RefreshTokenWithPinRequestDO) : ResultWrapper<TokenResponseDO>{
        return authRepository.getTokenWithPin(getTokenWithPinRequestDO)
    }
}