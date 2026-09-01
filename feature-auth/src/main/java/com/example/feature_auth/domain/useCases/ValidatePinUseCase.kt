package com.example.feature_auth.domain.useCases

class ValidatePinUseCase {
    fun validate(pin: String) : Boolean {
        if(pin.length != 4) return false

        if(pin.any { !it.isDigit() }) return false

        // Check if all digits are equal, or incremental
        return true
    }
}