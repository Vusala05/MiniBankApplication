package com.example.core.domain.feature

interface SessionTokenRefresher {

    suspend fun  refreshIfPossible() : RefreshedResult
}

sealed class RefreshedResult {
    data class Success(val requiredPinSet : Boolean) : RefreshedResult()
    data object Failed : RefreshedResult()
}