package com.example.feature_auth.ui.presentation.pinVerified

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.core.domain.feature.PinFlowChannel
import com.example.core.domain.useCase.HandleErrorUseCase
import com.example.core_ui.viewModel.BaseViewModel
import com.example.feature_auth.domain.useCases.SubmitPinResult
import com.example.feature_auth.domain.useCases.SubmitPinUseCase
import com.example.feature_auth.ui.presentation.AuthContract
import com.example.feature_auth.ui.presentation.AuthContract.IS_FROM_SESSION_AUTH
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PinVerifiedViewModel @Inject constructor(
    val handleErrorUseCase: HandleErrorUseCase,
    val pinFlowChannel: PinFlowChannel,
    val submitPinUseCase: SubmitPinUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AuthContract.State, AuthContract.Effect>(
    initialState = AuthContract.State(),
    handleErrorUseCase = handleErrorUseCase
) {
    private val isFromSessionAuth = savedStateHandle.get<Boolean>(IS_FROM_SESSION_AUTH) ?: false

    fun handleIntent(intent: AuthContract.Intent){
        when(intent){
            is AuthContract.Intent.OnPinChange -> {
                onPinChange(intent.newPin)
            }
            is AuthContract.Intent.SubmitPin -> {
                onSubmitPin()
            }
        }
    }

    private fun onPinChange(newPin : String){
        if(newPin.length > 4 || !newPin.all { it.isDigit() }) return

        updateState { it.copy(verifiedPin = newPin, showPinError = false, pinError = "") }
    }

    private fun onSubmitPin() {
        val currentState = currentState()
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            when(val res = submitPinUseCase(currentState.verifiedPin)){
                is SubmitPinResult.Success<*> -> {
                    updateState { it.copy(isLoading = false, pinError = "", showPinError = false) }
                    completePinFlow()

                }

                is SubmitPinResult.Invalid ->{
                    updateState { it.copy(isLoading = false,initialPin = "", showPinError = true, pinError = "PIN 4 rəqəmdən ibarət olmalıdır") }
                }

                is SubmitPinResult.Error ->{
                    updateState { it.copy(isLoading = false, initialPin = "") }
                    handleError(res.error)
                }


            }
        }

    }
    private suspend fun completePinFlow() {
        if(isFromSessionAuth){
            pinFlowChannel.sendPinResult(success = true)
        }
        sendEffect(AuthContract.Effect.PinFlowCompleted)
    }

    override fun showMessage(message: Int) {
        viewModelScope.launch {
            sendEffect(AuthContract.Effect.ShowMessage(message))
        }
    }


}