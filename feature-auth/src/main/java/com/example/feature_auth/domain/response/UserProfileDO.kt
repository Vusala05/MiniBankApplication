package com.example.feature_auth.domain.response

import kotlinx.serialization.SerialName

data class UserProfileDO(
    val id: String,
    val clientNo: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String
)