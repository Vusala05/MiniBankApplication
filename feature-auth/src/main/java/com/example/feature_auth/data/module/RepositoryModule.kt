package com.example.feature_auth.data.module

import com.example.feature_auth.data.repositoryImpl.UserAuthRepositoryImpl
import com.example.feature_auth.domain.repository.UserAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository (userAuthRepositoryImpl: UserAuthRepositoryImpl) : UserAuthRepository
}