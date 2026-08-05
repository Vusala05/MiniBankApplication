package com.example.feature_transaction.data.module

import com.example.feature_transaction.data.repositoryImpl.TransactionRepositoryImpl
import com.example.feature_transaction.domain.repository.TransactionRepository
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