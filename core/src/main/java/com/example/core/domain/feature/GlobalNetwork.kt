package com.example.core.domain.feature

import com.example.core.domain.model.ResultWrapper

interface GlobalNetwork {
    fun handleError(error:ResultWrapper.Error)
}