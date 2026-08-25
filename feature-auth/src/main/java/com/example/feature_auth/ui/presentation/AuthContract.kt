package com.example.feature_auth.ui.presentation

import com.example.navigation.Route

object AuthContract {

    sealed interface Effect {
        data class ShowMessage(val message : Int) : Effect
    }

    sealed interface Intent {
        data class OnPinChange(val newPin : String) : Intent
        data class NavigateProfileScreen (val route: Route) : Intent
    }

    data class State(
        val initialPin : String = "",
        val verifiedPin : String = "",
        val showPinError : Boolean = false,
        val pinStep : PinStep = PinStep.INITIAL){

        val currentPin : String
            get() = when(pinStep){
             PinStep.INITIAL -> initialPin
                PinStep.VERIFIED -> initialPin
                PinStep.SUCCESS -> ""
            }


        val currentPinLength : Int
            get() = currentPin.length
    }



    enum class PinStep{
        INITIAL,
        VERIFIED,
        SUCCESS
    }

}