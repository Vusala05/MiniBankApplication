package com.example.data.domain.request

data class UpdateUserProfileRequestDO(
    val firstName: String?=null,
    val lastName: String?=null,
    val email: String?=null,
    val phoneNumber: String?=null
)