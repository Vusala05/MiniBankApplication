package com.example.core.data.model

import com.example.core.domain.model.ErrorModelDo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("data")
    val data: T? = null,
    @SerialName("code")
    val code: Int = 0,
    @SerialName("message")
    val message: String = "SUCCESS",
) {
    fun toErrorModel(): ErrorModelDo {
        return ErrorModelDo(
            code = code,
            message = message
        )
    }
}
