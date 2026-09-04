package com.example.feature_auth.ui.presentation.pinSetUp

import androidx.lifecycle.viewModelScope
import com.example.core.domain.feature.PinFlowChannel
import com.example.core.domain.useCase.HandleErrorUseCase
import com.example.core_ui.viewModel.BaseViewModel
import com.example.feature_auth.domain.useCases.SubmitPinResult
import com.example.feature_auth.domain.useCases.SubmitPinUseCase
import com.example.feature_auth.ui.presentation.AuthContract
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PinSetUpViewModel @Inject constructor(
    val handleErrorUseCase: HandleErrorUseCase,
    val submitPinUseCase: SubmitPinUseCase,
    val pinFlowChannel: PinFlowChannel
) : BaseViewModel<AuthContract.State, AuthContract.Effect>(
    initialState = AuthContract.State(),
    handleErrorUseCase = handleErrorUseCase
)  {

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

        updateState { it.copy(initialPin = newPin, showPinError = false, pinError = "") }
    }

    private fun onSubmitPin() {
        val currentState = currentState()
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            when(val res = submitPinUseCase(currentState.initialPin)){
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

    private fun completePinFlow(){
        viewModelScope.launch {
            sendEffect(AuthContract.Effect.PinFlowCompleted)
        }
    }
    override fun showMessage(message: Int) {
        viewModelScope.launch {
            sendEffect(AuthContract.Effect.ShowMessage(message))
        }
    }


}