package com.example.feature_auth.data.module

import com.example.core.domain.feature.SessionTokenRefresher
import com.example.feature_auth.data.helper.SessionTokenRefreshImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthLocalDataModule {

   @Binds
    abstract fun bindSessionTokenRefresher(
      sessionTokenRefresherImpl: SessionTokenRefreshImpl
   ): SessionTokenRefresher
}