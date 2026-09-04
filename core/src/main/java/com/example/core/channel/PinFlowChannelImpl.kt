package com.example.core.channel

import com.example.core.domain.feature.PinFlowChannel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration.Companion.seconds

class PinFlowChannelImpl @Inject constructor() : PinFlowChannel {
    private var channel: Channel<Boolean> = Channel(capacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override suspend fun awaitPinResult(): Boolean {
        return try {
            withTimeout(30.seconds) { channel.receive() }
        } catch (_: Exception) {
            false
        }
    }

    override suspend fun sendPinResult(success: Boolean) {
        channel.send(success)
    }
}