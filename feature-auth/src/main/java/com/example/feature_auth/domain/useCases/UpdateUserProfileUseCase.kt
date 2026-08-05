package com.example.feature_auth.domain.useCases

import com.example.core.domain.model.ResultWrapper
import com.example.feature_auth.domain.repository.UserAuthRepository
import com.example.feature_auth.domain.request.UpdateUserProfileRequestDO
import com.example.feature_auth.domain.response.UserProfileDO
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    val authRepository: UserAuthRepository
) {
    suspend operator fun invoke(updateUserProfileRequestDO: UpdateUserProfileRequestDO) : ResultWrapper<UserProfileDO>{
        return authRepository.updateUserProfileProfile(updateUserProfileRequestDO)
    }
}