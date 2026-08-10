package com.example.core.data.module

import com.example.core.data.dataSource.InMemoryDataSource
import com.example.core.domain.feature.CacheManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    @Singleton
    abstract fun bindCacheManager( inMemoryDataSource: InMemoryDataSource) : CacheManager


}