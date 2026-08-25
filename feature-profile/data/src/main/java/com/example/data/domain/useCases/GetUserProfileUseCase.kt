package com.example.data.domain.useCases

import com.example.core.domain.model.ResultWrapper
import com.example.data.data.repositoryImpl.ProfileRepository
import com.example.data.domain.response.UserProfileDO
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    val profileRepository: ProfileRepository
) {
    suspend operator fun invoke() : ResultWrapper<UserProfileDO> {
        return profileRepository.getUseProfile()
    }
}