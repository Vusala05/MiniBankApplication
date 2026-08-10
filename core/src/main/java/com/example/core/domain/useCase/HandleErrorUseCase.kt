package com.example.core.domain.useCase

import com.example.core.domain.model.AppError
import com.example.core.domain.util.BusinessErrorTypeEnum
import com.example.core.domain.util.ErrorType
import com.example.core.domain.util.SystemErrorTypeEnum
import javax.inject.Inject


class HandleErrorUseCase @Inject constructor() {

    operator fun invoke(appError: AppError) : ErrorType{
        when(appError){
           is  AppError.SystemError ->{
              val error =  SystemErrorTypeEnum.findErrorType(appError.errorModel.code)
               return error
            }

            is AppError.BusinessError -> {
               val error =  BusinessErrorTypeEnum.getBusinessError(appError.errorModel.code)
                return error
            }
        }
    }
}