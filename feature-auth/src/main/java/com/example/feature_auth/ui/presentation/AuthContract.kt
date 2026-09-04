package com.example.feature_auth.ui.presentation

import com.example.navigation.Route

object AuthContract {

    sealed interface Effect {
        data class ShowMessage(val message : Int) : Effect
        data object PinFlowCompleted : Effect
    }

    sealed interface Intent {
        data class OnPinChange(val newPin : String) : Intent
        data object SubmitPin : Intent

    }

    data class State(
        val initialPin : String = "",
        val verifiedPin : String = "",
        val showPinError : Boolean = false,
        val pinError : String = "",
        val isLoading : Boolean = false){

        /*val currentPin : String
            get() = when(pinStep){
             PinStep.PIN_SETUP -> initialPin
                PinStep.PIN_VERIFIED -> verifiedPin
                PinStep.SUCCESS -> ""
            }


        val currentPinLength : Int
            get() = currentPin.length*/
    }
  const val IS_FROM_SESSION_AUTH = "isFromSessionAuth"


}