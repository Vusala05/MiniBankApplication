package com.example.data.data.response

import com.example.data.domain.response.UserProfileDO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    @SerialName("id")
    val id: String?=null,
    @SerialName("clientNo")
    val clientNo: String?=null,
    @SerialName("firstName")
    val firstName: String?=null,
    @SerialName("lastName")
    val lastName: String?=null,
    @SerialName("email")
    val email: String?=null,
    @SerialName("phoneNumber")
    val phoneNumber: String?=null,
    @SerialName("hasPin")
    val hasPin : Boolean?=null
){
    companion object{
        fun UserProfile.toDomain(): UserProfileDO {
            return UserProfileDO(
                id = this.id.orEmpty(),
                clientNo = this.clientNo.orEmpty(),
                firstName = this.firstName.orEmpty(),
                lastName = this.lastName.orEmpty(),
                email = this.email.orEmpty(),
                phoneNumber = this.phoneNumber.orEmpty(),
                hasPin = this.hasPin ?: false
            )
        }
    }
}