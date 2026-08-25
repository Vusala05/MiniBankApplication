package com.example.data.data.module

import com.example.data.data.repositoryImpl.ProfileRepository
import com.example.data.domain.repository.ProfileRepositoryImpl
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
    abstract fun bindProfileRepository (profileRepositoryImpl: ProfileRepositoryImpl) : ProfileRepository
}