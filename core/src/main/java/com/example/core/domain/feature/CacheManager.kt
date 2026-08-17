package com.example.core.domain.feature

interface CacheManager  {
    suspend  fun  writeData(key : String, value : String , expirationTime : Long)

    suspend fun getData (key : String, pullRequest : Boolean) : String?

}

