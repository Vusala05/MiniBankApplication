package com.example.core.domain.feature

interface CacheManager  {
    fun writeData(key : String, value : Any?, expirationTime : Long)

    fun getData (key : String, forceToRefresh : Boolean) : Any?
}