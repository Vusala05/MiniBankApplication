package com.example.ui.presentation

import com.example.data.domain.response.UserProfileDO
import com.example.navigation.Route

object ProfileContract {

    sealed interface Intent{
        data class SetName (val name : String) : Intent
        data class SetSurname (val surname : String) : Intent
        data class SetEmail (val email : String) : Intent
        data class SetPhone (val phone : String) : Intent
        data object OnSaveClick : Intent
        data class OnNextClick (val route : Route)  : Intent

    }

    sealed interface Effect{
        data class ShowErrorMessage(val message : Int) : Effect
    }

    data class State (
        val loading : Boolean = false,
        val name : String = " ",
        val surname : String = " ",
        val email : String = " ",
        val phone : String = " ",
        val userProfile : UserProfileDO = UserProfileDO("","","","","","",false)
    ){
        val  hasUnsavedChanges : Boolean
            get() = userProfile.firstName != name ||
                    userProfile.lastName != surname ||
                    userProfile.email != email ||
                    userProfile.phoneNumber != phone

        val buttonEnable = hasUnsavedChanges || !loading

    }
}