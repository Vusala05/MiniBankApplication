package com.example.feature_auth.data.request

import com.example.feature_auth.domain.request.UpdateUserProfileRequestDO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserProfileRequest(
    @SerialName("firstName")
    val firstName: String? = null,
    @SerialName("lastName")
    val lastName: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("phoneNumber")
    val phoneNumber: String? = null
){
    companion object{
        fun UpdateUserProfileRequestDO.toEntity() : UpdateUserProfileRequest{
            return UpdateUserProfileRequest(
                firstName = firstName,
                lastName = lastName,
                email = email,
                phoneNumber = phoneNumber
            )
        }
    }
}
