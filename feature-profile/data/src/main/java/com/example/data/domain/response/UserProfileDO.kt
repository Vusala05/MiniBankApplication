package com.example.data.domain.response

data class UserProfileDO(
    val id: String,
    val clientNo: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val hasPin : Boolean
)