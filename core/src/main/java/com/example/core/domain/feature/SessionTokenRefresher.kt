package com.example.core.domain.feature

interface SessionTokenRefresher {

    suspend fun  refreshIfPossible() : Boolean
}