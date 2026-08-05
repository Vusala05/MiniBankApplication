package com.example.data.data.module

import com.example.data.domain.repository.UserCardInfoRepository
import com.example.feature_card.data.repositoryImpl.UserCardInfoRepositoryImpl
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
    abstract fun bindCardRepository (userCardInfoRepositoryImpl: UserCardInfoRepositoryImpl) : UserCardInfoRepository
}