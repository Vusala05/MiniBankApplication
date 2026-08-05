package com.example.core_ui.util

import com.example.core_ui.R

enum class BusinessErrorTypeEnum(
   val errorCode : Int,
   val errorMessage : Int,
) {
    CARD_NOT_FOUND(
        errorCode = 1001,
        errorMessage = R.string.error_card_not_found
    ),

    INSUFFICIENT_FUNDS(
        errorCode = 1002,
        errorMessage = R.string.error_insufficient_funds
    ),

    INVALID_REFRESH_TOKEN(
        errorCode = 1003,
        errorMessage = R.string.error_session_expired,
    ),

    INVALID_TRANSFER_AMOUNT(
        errorCode = 1004,
        errorMessage = R.string.error_invalid_amount
    ),

    CARD_NOT_ACTIVE(
        errorCode = 1005,
        errorMessage = R.string.error_card_not_active
    ),
    SAME_CARD_TRANSFER(
        errorCode = 1006,
        errorMessage = R.string.error_same_card
    ),
    CURRENCY_MISMATCH(
        errorCode = 1007,
        errorMessage = R.string.error_currency_mismatch
    ),
    INVALID_PROFILE_REQUEST(
        errorCode = 1008,
        errorMessage = R.string.error_invalid_profile_request
    ),
    UNKNOWN_ERROR(
        errorCode = Integer.MAX_VALUE,
        errorMessage = R.string.unknown_error
    );

    companion object {
        fun getBusinessError(code: Int?): BusinessErrorTypeEnum {
            val errorType = entries.find { it.errorCode == code }
            return errorType ?: UNKNOWN_ERROR
        }
    }


}