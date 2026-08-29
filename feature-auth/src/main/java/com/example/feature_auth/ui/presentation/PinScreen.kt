package com.example.feature_auth.ui.presentation

import androidx.compose.runtime.Composable
import com.example.feature_auth.ui.components.PinScreenComponent
import com.example.feature_auth.ui.util.PinStep
import com.example.navigation.DeeplinkNavigator
import com.example.navigation.Route

@Composable
fun PinScreen(
    state : AuthContract.State,
    handleIntent: (AuthContract.Intent) -> Unit
) {
    val pinStepTitle = if(state.pinStep == PinStep.PIN_SETUP) "NEW PIN" else "VERIFY PIN"
    val pinStepDescription =  if(state.pinStep == PinStep.PIN_SETUP) "Set new pin " else "Verify pin to continue"
    PinScreenComponent(
        pinStepTitle = pinStepTitle,
        pinStepDescription = pinStepDescription,
        state = state,
        onPinChange = {
            handleIntent(AuthContract.Intent.OnPinChange(it))
        },
        pinError = state.pinError,
        onContinueClick = {
                handleIntent(AuthContract.Intent.SubmitPin)
        }

    )

}