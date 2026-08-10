package com.example.core.domain.model

sealed interface ResultWrapper<out T> {
    data class Success<T>(val data : T) : ResultWrapper<T>
    data class Error(
        val error: AppError,
    ) : ResultWrapper<Nothing>
}

inline fun <reified T, reified R> handleResultWrapper(
    result : ResultWrapper<T>,
    transform : (T) -> R
) : ResultWrapper<R> {
   return when(result){
        is ResultWrapper.Error -> ResultWrapper.Error(
           error = result.error
        )
        is ResultWrapper.Success ->
            try {
                ResultWrapper.Success(data = transform(result.data))
            } catch (e: Exception){
              e.printStackTrace()
                ResultWrapper.Error(
                    error = AppError.SystemError(
                        errorModel = ErrorModelDo(
                            message = e.message,
                            code = Integer.MAX_VALUE - 4
                        ),
                        exception = e
                    )
                )
            }
    }
}