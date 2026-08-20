package com.example.core.domain.feature

interface CacheManager  {
    suspend  fun  writeData( key : String, groupKey : String ,value : String , expirationTime : Long )

    suspend fun getData ( key : String) : String?

    suspend fun invalidateGroupKey( groupKey : String )

}

