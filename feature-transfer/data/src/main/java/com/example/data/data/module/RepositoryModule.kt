package com.example.feature_transfer.data.module

import com.example.data.data.repositoryImpl.TransferRepositoryImpl
import com.example.data.domain.repository.TransferRepository
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
    abstract fun bindTransferRepository (transferRepositoryImpl: TransferRepositoryImpl) : TransferRepository
}