package com.example.core.data.network

import com.example.core.data.model.BaseResponse
import com.example.core.domain.feature.GlobalNetwork
import com.example.core.domain.model.ErrorModelDo
import com.example.core.domain.model.ResultWrapper
import kotlinx.serialization.json.Json
import okio.IOException
import retrofit2.Response
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

inline fun <reified T> apiCallingHandler(
    globalNetwork: GlobalNetwork,
    apiCall : () -> Response<BaseResponse<T>>
) : ResultWrapper<T?> {
     return try {
         val result = apiCall()
         if(result.isSuccessful){
             ResultWrapper.Success(data = result.body()?.data )
         }
         else{
             val errorBodyString = result.errorBody()?.string() ?: "Unknown Error Occured"
             val errorModel = if(errorBodyString.isBlank()){
                  ErrorModelDo(code = result.code(), message = "Server error: ${result.code()}")
             } else{
                 try {
                     Json.decodeFromString<BaseResponse<Unit>>(errorBodyString).toErrorModel()
                 } catch (e: Exception) {
                     ErrorModelDo(code = result.code(), message = "Server error: ${result.code()}")
                 }
             }
             val exception = if (result.code() == UNAUTHORIZED_CODE) {
                 UnauthorizedException()
             } else {
                 Exception()
             }
             globalNetwork.handleError(
                 error = ResultWrapper.Error(
                     exception = exception,
                     message = errorModel.message,
                     code = errorModel.code
                 )
             )
             ResultWrapper.Error(
                 exception = exception,
                 message = errorModel.message,
                 code = errorModel.code
             )

         }
     } catch (throwable : Throwable){
             throwable.printStackTrace()
             when (throwable) {
                 is UnknownHostException,
                 is SSLHandshakeException,
                 is SocketTimeoutException,
                 is SocketException -> {
                     val error = ResultWrapper.Error(
                         message = throwable.message,
                         code = Integer.MAX_VALUE-1,
                         exception = throwable

                     )
                     globalNetwork.handleError(
                         error
                     )
                    return error
                 }

                 is IOException -> {
                     val error = ResultWrapper.Error(
                         message = throwable.message,
                         code = Integer.MAX_VALUE - 2,
                         exception = throwable
                     )
                     globalNetwork.handleError(
                         error
                     )
                     return error
                 }

                 else -> {
                     val error = ResultWrapper.Error(
                         exception = Exception(throwable.message, throwable),
                         message = SOMETHING_WENT_WRONG,
                         code = Integer.MAX_VALUE - 3
                     )
                     globalNetwork.handleError(
                         error
                     )
                     return error
                 }
             }

         }
     }


const val UNAUTHORIZED_CODE = 401
const val SOMETHING_WENT_WRONG = "SomeThing went wrong"
class UnauthorizedException : Exception()
