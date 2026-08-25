package com.example.data.domain.useCases

import com.example.core.domain.model.ResultWrapper
import com.example.data.data.repositoryImpl.ProfileRepository
import com.example.data.domain.request.UpdateUserProfileRequestDO
import com.example.data.domain.response.UserProfileDO
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    val authRepository: ProfileRepository
) {
    suspend operator fun invoke(updateUserProfileRequestDO: UpdateUserProfileRequestDO) : ResultWrapper<UserProfileDO> {
        return authRepository.updateUserProfileProfile(updateUserProfileRequestDO)
    }
}