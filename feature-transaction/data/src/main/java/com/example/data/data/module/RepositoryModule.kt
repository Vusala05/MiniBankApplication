package com.example.data.data.module

import com.example.data.data.repositoryImpl.TransactionRepositoryImpl
import com.example.data.domain.repository.TransactionRepository
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
    abstract fun bindTransactionRepository (transactionRepositoryImpl: TransactionRepositoryImpl) : TransactionRepository

}