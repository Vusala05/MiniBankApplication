package com.example.core.domain.model

sealed interface AppError {

    data class BusinessError(
        val  errorModel: ErrorModelDo,
        val  exception: Exception
    ) : AppError

    data class SystemError(
        val errorModel: ErrorModelDo,
        val  exception: Exception
    ) : AppError
}