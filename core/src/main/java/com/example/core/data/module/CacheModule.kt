package com.example.core.data.module

import com.example.core.data.dataSource.InMemoryDataSource
import com.example.core.data.dataSource.LocalDataSource
import com.example.core.domain.feature.CacheManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Qualifier
    annotation class InMemoryCacheManager

    @Qualifier
    annotation class LocalCacheManager

    @InMemoryCacheManager
    @Binds
    @Singleton
    abstract fun bindInMemoryCacheManager( inMemoryDataSource: InMemoryDataSource) : CacheManager

    @LocalCacheManager
    @Binds
    @Singleton
    abstract fun bindLocalCacheManager( localDataSource: LocalDataSource ) : CacheManager




}