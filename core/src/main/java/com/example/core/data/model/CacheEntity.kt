package com.example.core.data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "CacheTable")
data class CacheEntity(
    @PrimaryKey
    val key : String,
    val value : String?,
    val expirationTime : Long,
    val addedAtTime : Long
)