package com.example.core.domain.model

data class ErrorModelDo(
    val exception: Exception?=null,
    val message : String?=null,
    val code : Int?=null
)