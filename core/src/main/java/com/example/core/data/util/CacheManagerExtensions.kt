package com.example.core.data.util

import com.example.core.domain.feature.CacheManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

suspend inline fun <reified T> CacheManager.writeAndConvertToJson(key : String, groupKey : String?=null, value : T, ttl : Long) {
    val convertedToString =  Json.encodeToString(value)
    val groupKey = groupKey ?: key
    writeData(key, groupKey,convertedToString, ttl)
}
suspend  inline fun <reified T> CacheManager.getAndConvertToModel(key : String) : T? {
    val json = getData(key) ?: return null
    try {
        return Json.decodeFromString<T>(json)
    } catch (e : Exception){
        e.printStackTrace()
        return null
    }


}