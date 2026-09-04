package com.example.feature_auth.ui.presentation.pinVerified

import androidx.compose.runtime.Composable
import com.example.feature_auth.ui.components.PinScreenComponent
import com.example.feature_auth.ui.presentation.AuthContract

@Composable
fun PinVerifiedScreen(
    state: AuthContract.State,
    handleIntent: (AuthContract.Intent) -> Unit
) {
    val pinStepTitle = "VERIFY PIN"
    val pinStepDescription = "Verify pin to continue"
    PinScreenComponent(
        pinStepTitle = pinStepTitle,
        pinStepDescription = pinStepDescription,
        showPinError = state.showPinError,
        currentPin = state.verifiedPin,
        onPinChange = {
            handleIntent(AuthContract.Intent.OnPinChange(it))
        },
        pinError = state.pinError,
        onContinueClick = {
            handleIntent(AuthContract.Intent.SubmitPin)
        }

    )

}