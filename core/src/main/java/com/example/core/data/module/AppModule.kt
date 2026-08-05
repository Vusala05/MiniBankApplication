package com.example.core.data.module

import com.example.core.data.network.ApiErrorHandler
import com.example.core.domain.feature.GlobalNetwork
import com.example.core.domain.feature.SessionTokenRefresher
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindGlobalNetwork( apiErrorHandler: ApiErrorHandler) : GlobalNetwork


    companion object {
        @Provides
        @Singleton
        fun provideApplicationScope(): CoroutineScope =
            CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
}