package com.example.core.domain.model

sealed interface ResultWrapper<out T> {
    data class Success<T>(val data : T) : ResultWrapper<T>
    data class Error(
        val exception: Exception?=null,
        val message : String ?=null,
        val code : Int ?=null
    ) : ResultWrapper<Nothing>
}

inline fun <reified T, reified R> handleResultWrapper(
    result : ResultWrapper<T>,
    transform : (T) -> R
) : ResultWrapper<R> {
   return when(result){
        is ResultWrapper.Error -> ResultWrapper.Error(
            exception = result.exception,
            message = result.message,
            code = result.code
        )
        is ResultWrapper.Success ->
            try {
                ResultWrapper.Success(data = transform(result.data))
            } catch (e: Exception){
              e.printStackTrace()
                ResultWrapper.Error(exception = e)
            }
    }
}