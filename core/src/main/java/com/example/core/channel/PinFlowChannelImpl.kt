package com.example.core.channel

import androidx.room3.concurrent.AtomicBoolean
import com.example.core.domain.feature.PinFlowChannel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel

class PinFlowChannelImpl @Inject constructor() : PinFlowChannel {
    private var channel: Channel<Boolean>? = null
    val isCompleted = AtomicBoolean(false)
    override suspend fun awaitPinResult(): Boolean {
        val newChannel = Channel<Boolean>()
        channel = newChannel
        isCompleted.set(false)
        val result = newChannel.receive()
        return result
    }

    override suspend fun sendPinResult(success: Boolean) {
        channel?.let {
            if (isCompleted.compareAndSet(false,true)) {
                it.send(success)
            }
        }

    }

    override fun hasPendingFlow(): Boolean {
        return channel != null && !isCompleted.get()
    }
}