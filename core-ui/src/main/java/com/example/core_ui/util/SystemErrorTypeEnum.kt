package com.example.core_ui.util

import com.example.core_ui.R

enum class SystemErrorTypeEnum(
    val errorCode : Int,
    val errorMessage : Int
) {

     NETWORK_ERROR(
         errorCode = Integer.MAX_VALUE - 1,
         errorMessage = R.string.network_error
     ),

    NETWORK_IO_ERROR(
        errorCode = Integer.MAX_VALUE - 2,
        errorMessage = R.string.network_io_error
    ),

    NETWORK_UNKNOWN_ERROR(
    errorCode = Integer.MAX_VALUE - 3,
    errorMessage = R.string.network_unknown_error
    );
    companion object {
        fun findErrorType(code: Int?): SystemErrorTypeEnum? {
            return entries.find { it.errorCode == code }
        }

        fun isSystemError(code: Int?) : Boolean {
            return findErrorType(code) != null
        }
    }

}