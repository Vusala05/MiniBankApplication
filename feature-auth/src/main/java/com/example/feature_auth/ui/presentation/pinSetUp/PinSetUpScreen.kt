package com.example.feature_auth.ui.presentation.pinSetUp

import androidx.compose.runtime.Composable
import com.example.feature_auth.ui.components.PinScreenComponent
import com.example.feature_auth.ui.presentation.AuthContract

@Composable
fun PinSetUpScreen(
    state: AuthContract.State,
    handleIntent: (AuthContract.Intent) -> Unit
) {
    val pinStepTitle = "NEW PIN"
    val pinStepDescription = "Set new pin "
    PinScreenComponent(
        pinStepTitle = pinStepTitle,
        pinStepDescription = pinStepDescription,
        currentPin = state.initialPin,
        showPinError = state.showPinError,
        onPinChange = {
            handleIntent(AuthContract.Intent.OnPinChange(it))
        },
        pinError = state.pinError,
        onContinueClick = {
            handleIntent(AuthContract.Intent.SubmitPin)
        }

    )

}