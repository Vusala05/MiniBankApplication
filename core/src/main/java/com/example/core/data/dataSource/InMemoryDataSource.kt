package com.example.core.data.dataSource

import com.example.core.domain.feature.CacheManager
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class InMemoryDataSource @Inject constructor() : CacheManager {
    var cachedEntry = ConcurrentHashMap<String, InMemoryDataEntry>()

    override fun writeData(key: String, value: Any?, expirationTime: Long) {
    cachedEntry[key] = InMemoryDataEntry(value,expirationTime, System.currentTimeMillis())

    }

    override fun getData(key: String,forceToRefresh : Boolean): Any? {
        val matchedCache = cachedEntry[key]
        matchedCache?.let {
            val timeIsValid = System.nanoTime() - matchedCache.writeAtMillis < matchedCache.expirationDuration
            if(matchedCache.data!=null && timeIsValid && !forceToRefresh ){
                return matchedCache.data
            }
        }

        return null
    }
}

data class InMemoryDataEntry(
    val data : Any?,
    val expirationDuration : Long,
    val writeAtMillis : Long
)