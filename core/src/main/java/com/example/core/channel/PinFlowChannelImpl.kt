package com.example.core.channel

import com.example.core.domain.feature.PinFlowChannel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel

class PinFlowChannelImpl @Inject constructor() : PinFlowChannel {
    private var channel: Channel<Boolean>? = null
    @Volatile
    private var isCompleted = false
    override suspend fun awaitPinResult(): Boolean {
        val newChannel = Channel<Boolean>()
        channel = newChannel
        isCompleted = false
        val result = newChannel.receive()
        isCompleted = true
        return result
    }

    override suspend fun sendPinResult(success: Boolean) {
        if (isCompleted) return
        channel?.send(success)
    }

    override fun hasPendingFlow(): Boolean {
        return channel != null && !isCompleted
    }
}