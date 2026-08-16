package com.example.core.data.module

import android.content.Context
import androidx.room3.Room
import com.example.core.data.dataSource.AppDatabase
import com.example.core.data.dataSource.CacheDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "APP_DATABASE"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: AppDatabase): CacheDao {
        return database.cacheDao()
    }
}