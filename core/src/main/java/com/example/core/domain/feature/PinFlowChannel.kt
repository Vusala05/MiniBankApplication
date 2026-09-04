package com.example.core.domain.feature

interface PinFlowChannel {
    suspend fun awaitPinResult(): Boolean
    suspend fun sendPinResult(success: Boolean)
}