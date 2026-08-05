package com.example.core.data.network

import com.example.core.domain.feature.GlobalNetwork
import com.example.core.domain.model.ResultWrapper
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiErrorHandler @Inject constructor(
    val coroutineScope: CoroutineScope
) : GlobalNetwork {
    private val _effect = MutableSharedFlow<ResultWrapper.Error>()
    val effect  = _effect.asSharedFlow()
    override  fun handleError(error: ResultWrapper.Error) {
        coroutineScope.launch {
            if(error.exception !is CancellationException)
         _effect.emit(error)

        }

    }
}