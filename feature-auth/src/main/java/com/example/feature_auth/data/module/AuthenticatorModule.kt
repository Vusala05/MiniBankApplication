package com.example.feature_auth.data.module

import com.example.feature_auth.data.helper.SessionAuthenticator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.Authenticator

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticatorModule {
    @Binds
    @Singleton
    abstract fun bindAuthenticator(sessionAuthenticator: SessionAuthenticator): Authenticator
}