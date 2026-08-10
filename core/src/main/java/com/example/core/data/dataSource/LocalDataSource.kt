package com.example.core.data.dataSource

import com.example.core.domain.feature.CacheManager

class LocalDataSource : CacheManager {
    override fun writeData(key: String, value: Any?, expirationTime: Long) {
        TODO("Not yet implemented")
    }

    override fun getData(key: String, forceToRefresh: Boolean): Any? {
        TODO("Not yet implemented")
    }
}