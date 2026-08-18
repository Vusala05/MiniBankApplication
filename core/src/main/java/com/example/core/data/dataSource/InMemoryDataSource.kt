package com.example.core.data.dataSource

import com.example.core.domain.feature.CacheManager
import kotlinx.serialization.Serializable
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class InMemoryDataSource @Inject constructor() : CacheManager {
    var cachedEntry = ConcurrentHashMap<String, InMemoryDataEntry>()

    override suspend fun  writeData(key: String, value: String, expirationTime: Long) {
    cachedEntry[key] = InMemoryDataEntry(value,expirationTime, System.currentTimeMillis())

    }

    override suspend fun getData(key: String,pullRequest : Boolean): String? {
        val matchedCache = cachedEntry[key]
        matchedCache?.let {
            val timeIsOver = matchedCache.expirationDuration <= System.currentTimeMillis() - matchedCache.writeAtMillis
            if(timeIsOver || pullRequest){
                cachedEntry.remove(key)
                return null
            }
            if(matchedCache.data!=null ){
                return matchedCache.data
            }
        }
        return null
    }

    override suspend fun invalidateKeys(keys: List<String>) {
        keys.forEach { key ->
            cachedEntry.remove(key)
        }
    }

}
@Serializable
data class InMemoryDataEntry(
    val data : String?,
    val expirationDuration : Long,
    val writeAtMillis : Long
)