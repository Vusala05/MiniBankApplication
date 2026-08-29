package com.example.feature_auth.ui.presentation

import androidx.lifecycle.viewModelScope
import com.example.core.data.interceptor.TokenInterceptor
import com.example.core.domain.feature.PinFlowChannel
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.useCase.HandleErrorUseCase
import com.example.core_ui.viewModel.BaseViewModel
import com.example.feature_auth.data.dataSource.AuthLocalDataSource
import com.example.feature_auth.domain.request.RefreshTokenWithPinRequestDO
import com.example.feature_auth.domain.useCases.GetTokenWithPinUseCase
import com.example.feature_auth.ui.util.PinStep
import com.example.navigation.DeeplinkNavigator
import com.example.navigation.Navigator
import com.example.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    val handleErrorUseCase: HandleErrorUseCase,
    val authLocalDataSource: AuthLocalDataSource,
    val getTokenWithPinUseCase: GetTokenWithPinUseCase,
    val pinFlowChannel: PinFlowChannel,
    val tokenInterceptor: TokenInterceptor,
    val navigator: Navigator
) : BaseViewModel<AuthContract.State, AuthContract.Effect>(
    initialState = AuthContract.State(),
    handleErrorUseCase = handleErrorUseCase
) {

    fun handleIntent(intent: AuthContract.Intent) {
        when (intent) {
            is AuthContract.Intent.OnPinChange -> {
                onPinChange(intent.newPin)
            }

            is AuthContract.Intent.SubmitPin ->{
                submitPin()
            }

        }
    }

    private fun onPinChange(newPin: String) {
        if (newPin.length > 4 || !newPin.all { it.isDigit() }) return

        val currentState = currentState()
        when (currentState.pinStep) {
            PinStep.PIN_SETUP -> updateState {
                it.copy(
                    initialPin = newPin,
                    pinError = "",
                    showPinError = false
                )
            }

            PinStep.PIN_VERIFIED -> updateState {
                it.copy(
                    verifiedPin = newPin,
                    pinError = "",
                    showPinError = false
                )
            }

            PinStep.SUCCESS -> Unit
        }
    }

    private fun submitPin() {
        val currentState = currentState()
        val pin = currentState.currentPin

        if (pin.length != 4) {
            updateState { it.copy(pinError = "PIN 4 rəqəmdən ibarət olmalıdır", showPinError = true,
                initialPin = "", verifiedPin = "") }
            return
        }

        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            when (val res = getTokenWithPinUseCase(RefreshTokenWithPinRequestDO(pin = pin))) {
                is ResultWrapper.Success -> {
                    val tokens = res.data
                    authLocalDataSource.saveAccessToken(tokens.accessToken)
                    authLocalDataSource.saveRefreshToken(tokens.refreshToken)
                    tokenInterceptor.accessToken = tokens.accessToken

                    updateState { it.copy(isLoading = false) }
                    completePinFlow()
                }
                is ResultWrapper.Error -> {
                    updateState { it.copy(isLoading = false, verifiedPin = "", initialPin = "") }
                    handleError(res.error)
                }
            }
        }
    }
    private suspend fun completePinFlow() {
        updateState { it.copy(pinStep = PinStep.SUCCESS) }
        pinFlowChannel.sendPinResult(success = true)
        sendEffect(AuthContract.Effect.PinFlowCompleted)
    }

    override fun showMessage(message: Int) {
        viewModelScope.launch {
            sendEffect(AuthContract.Effect.ShowMessage(message))
        }
    }


}

