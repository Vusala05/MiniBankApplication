package com.example.core.domain.util

import com.example.core.R

enum class SystemErrorTypeEnum(
    override val errorCode : Int,
    override val errorMessage : Int
) : ErrorType {

     NETWORK_ERROR(
         errorCode = Integer.MAX_VALUE - 1,
         errorMessage = R.string.network_error
     ),

    NETWORK_IO_ERROR(
        errorCode = Integer.MAX_VALUE - 2,
        errorMessage = R.string.network_io_error
    ),
    INTERNAL_MAPPING_ERROR(
        errorCode = Integer.MAX_VALUE - 4,
        errorMessage = R.string.network_io_error
    ),

    NETWORK_UNKNOWN_ERROR(
    errorCode = Integer.MAX_VALUE,
    errorMessage = R.string.network_unknown_error
    );

    companion object {
        fun findErrorType(code: Int?): SystemErrorTypeEnum {
            return entries.find { it.errorCode == code } ?: NETWORK_UNKNOWN_ERROR
        }


    }

}