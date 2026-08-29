package com.example.data.data.module

import com.example.core.data.module.NetworkModule
import com.example.data.data.dataSource.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object  ProfileApiModule {
    @Provides
    @Singleton
    fun provideProfileDataSource(
        @Named("Main-Retrofit") retrofit: Retrofit) =
        retrofit.create(DataSource::class.java)
}