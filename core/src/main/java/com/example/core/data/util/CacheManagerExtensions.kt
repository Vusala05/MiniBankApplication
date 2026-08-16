package com.example.core.data.util

import com.example.core.domain.feature.CacheManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

suspend inline fun <reified T> CacheManager.writeAndConvertToJson(key : String, value : T, ttl : Long) {
    val convertedToString =  Json.encodeToString(value)
    writeData(key, convertedToString, ttl)
}
suspend  inline fun <reified T> CacheManager.getAndConvertToModel(key : String, pullRequest: Boolean) : T? {
    val json = getData(key,pullRequest) ?: return null
    try {
        return Json.decodeFromString<T>(json)
    } catch (e : Exception){
        e.printStackTrace()
        return null
    }


}