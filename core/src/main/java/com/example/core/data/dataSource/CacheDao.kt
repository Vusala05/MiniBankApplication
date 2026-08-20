package com.example.core.data.dataSource

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.core.data.model.CacheEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CacheDao {

    @Query("SELECT * FROM CacheTable WHERE key = :key")
    suspend fun getData(key : String) : CacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData (dataEntity : CacheEntity)

    @Query("DELETE FROM CacheTable WHERE key = :key ")
    suspend fun removeData(key : String)

    @Query("DELETE FROM CacheTable WHERE groupKey = :groupKey")
    suspend fun removeDataGroup(groupKey : String)

}