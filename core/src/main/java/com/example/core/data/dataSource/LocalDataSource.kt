package com.example.core.data.dataSource

import com.example.core.data.model.CacheEntity
import com.example.core.domain.feature.CacheManager
import javax.inject.Inject

class LocalDataSource @Inject constructor(val dao: CacheDao) : CacheManager {

    override suspend fun writeData(key: String, groupKey : String, value: String, expirationTime: Long) {
        dao.insertData(CacheEntity(key, groupKey, value , expirationTime, addedAtTime = System.currentTimeMillis()))
    }

    override suspend fun getData(key: String): String? {
        val dataEntity = dao.getData(key) ?: return null
                val timeIsNotValid = dataEntity.expirationTime <= System.currentTimeMillis() - dataEntity.addedAtTime
                if(timeIsNotValid){
                    dao.removeData(key)
                    return null
                }
                return dataEntity.value

    }

    override suspend fun invalidateGroupKey(groupKey: String) {
        dao.removeDataGroup(groupKey)
    }


}

