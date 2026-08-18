package com.example.core.domain.useCase

import com.example.core.domain.feature.CacheManager

class DeleteInvalidateKeysUseCase(val cacheManager: CacheManager) {
    suspend operator fun invoke(keys: String){
        cacheManager.invalidateKey(keys)
    }
}