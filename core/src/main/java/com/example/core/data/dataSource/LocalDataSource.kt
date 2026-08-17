package com.example.core.data.dataSource

import com.example.core.data.model.CacheEntity
import com.example.core.domain.feature.CacheManager
import javax.inject.Inject

class LocalDataSource @Inject constructor(val dao: CacheDao) : CacheManager {

    override suspend fun writeData(key: String, value: String, expirationTime: Long) {
        dao.insertData(CacheEntity(key, value , expirationTime, addedAtTime = System.currentTimeMillis()))
    }

    override suspend fun getData(key: String, pullRequest: Boolean): String? {
        val dataEntity = dao.getData(key) ?: return null
                val timeIsNotValid = dataEntity.expirationTime <= System.currentTimeMillis() - dataEntity.addedAtTime
                if(timeIsNotValid || pullRequest){
                    dao.removeData(key)
                    return null
                }
                return dataEntity.value

    }

}

