package com.example.feature_transaction.data.module

import com.example.core.data.module.NetworkModule
import com.example.feature_transaction.data.dataSource.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object  TransactionApiModule {
    @Provides
    @Singleton
    fun provideTransactionDataSource( retrofit: Retrofit) =
        retrofit.create(DataSource::class.java)
}