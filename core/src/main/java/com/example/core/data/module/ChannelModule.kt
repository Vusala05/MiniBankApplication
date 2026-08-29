package com.example.core.data.module

import com.example.core.channel.PinFlowChannelImpl
import com.example.core.domain.feature.PinFlowChannel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ChannelModule {

    @Binds
    @Singleton
    abstract fun bindPinFlowCoordinator(
        pinFlowChannelImpl: PinFlowChannelImpl
    ): PinFlowChannel
}