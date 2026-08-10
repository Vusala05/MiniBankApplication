package com.example.minibankapp

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.core.data.network.ApiErrorHandler
import com.example.core.domain.model.AppError
import com.example.core.domain.useCase.HandleErrorUseCase
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GlobalErrorObserver(
    apiErrorHandler: ApiErrorHandler,
    handleErrorUseCase: HandleErrorUseCase
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        apiErrorHandler.effect.collectLatest {
            if (it.error !is AppError.SystemError) return@collectLatest

            val error = handleErrorUseCase(it.error)

            Toast.makeText(context, error.errorMessage, Toast.LENGTH_LONG).show()


        }
    }
}