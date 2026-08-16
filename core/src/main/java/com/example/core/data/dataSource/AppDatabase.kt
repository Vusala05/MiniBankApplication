package com.example.core.data.dataSource

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.core.data.model.CacheEntity


@Database(entities = [CacheEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cacheDao() : CacheDao
}