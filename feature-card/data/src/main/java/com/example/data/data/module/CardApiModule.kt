package com.example.data.data.module

import com.example.core.data.module.NetworkModule
import com.example.feature_card.data.dataSource.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object  CardApiModule {
    @Provides
    @Singleton
    fun provideUsercardDataSource( retrofit: Retrofit) =
        retrofit.create(DataSource::class.java)
}