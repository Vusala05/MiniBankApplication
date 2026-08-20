package com.example.core.data.dataSource

import com.example.core.domain.feature.CacheManager
import kotlinx.serialization.Serializable
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class InMemoryDataSource @Inject constructor() : CacheManager {
    var cachedEntry = ConcurrentHashMap<String, InMemoryDataEntry>()

    override suspend fun  writeData(key: String, groupKey : String, value: String, expirationTime: Long) {
    cachedEntry[key] = InMemoryDataEntry(value,  groupKey,expirationTime, System.currentTimeMillis())

    }

    override suspend fun getData(key: String): String? {
        val matchedCache = cachedEntry[key]
        matchedCache?.let {
            val timeIsOver = matchedCache.expirationDuration <= System.currentTimeMillis() - matchedCache.writeAtMillis
            if(timeIsOver){
                cachedEntry.remove(key)
                return null
            }
            if(matchedCache.data!=null ){
                return matchedCache.data
            }
        }
        return null
    }

    override suspend fun invalidateGroupKey(groupKey: String) {
        cachedEntry.entries.removeIf{it.value.groupKey == groupKey}

    }


}
@Serializable
data class InMemoryDataEntry(
    val data : String?,
    val groupKey : String,
    val expirationDuration : Long,
    val writeAtMillis : Long
)