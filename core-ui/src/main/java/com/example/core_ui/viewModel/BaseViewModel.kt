package com.example.core_ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.core.domain.model.AppError
import com.example.core.domain.useCase.HandleErrorUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<S, E>(
    initialState: S,
    private val handleErrorUseCase: HandleErrorUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<E>()
    val effect = _effect.asSharedFlow()

    protected fun currentState(): S = _state.value

    protected fun updateState(update: (S) -> S) {
        _state.update(update)
    }

    protected suspend fun sendEffect(effect: E) {
        _effect.emit(effect)
    }

    protected fun handleError(error: AppError) {
        when (error) {
            is AppError.SystemError -> {
            }
            is AppError.BusinessError -> {
                val mapped = handleErrorUseCase(error)
                showMessage(mapped.errorMessage)
            }
        }
    }

    protected abstract fun showMessage(message: Int)
}

