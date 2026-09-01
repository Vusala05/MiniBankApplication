package com.example.feature_auth.domain.useCases

import com.example.core.data.interceptor.TokenInterceptor
import com.example.core.domain.model.AppError
import com.example.core.domain.model.ResultWrapper
import com.example.feature_auth.data.dataSource.AuthLocalDataStore
import com.example.feature_auth.domain.request.RefreshTokenWithPinRequestDO
import javax.inject.Inject

class SubmitPinUseCase @Inject constructor(
    val authLocalDataStore: AuthLocalDataStore,
    val getTokenWithPinUseCase: GetTokenWithPinUseCase,
    val tokenInterceptor: TokenInterceptor
){
    suspend operator fun invoke(pin : String) : SubmitPinResult{
      if(pin.length!=4){
          return SubmitPinResult.Invalid
      }
        when(val res = getTokenWithPinUseCase(RefreshTokenWithPinRequestDO(
            pin = pin
        ))){
            is ResultWrapper.Success -> {
                authLocalDataStore.saveAccessToken(res.data.accessToken)
                authLocalDataStore.saveRefreshToken(res.data.refreshToken)
                tokenInterceptor.accessToken = res.data.accessToken
                return SubmitPinResult.Success(data = res.data)

            }
            is ResultWrapper.Error ->{
                return SubmitPinResult.Error(error = res.error)
            }
        }

    }
}

sealed interface SubmitPinResult{
    data class Success<T>(val data : T) : SubmitPinResult
    data object Invalid : SubmitPinResult
    data class Error(val error : AppError) : SubmitPinResult
}


