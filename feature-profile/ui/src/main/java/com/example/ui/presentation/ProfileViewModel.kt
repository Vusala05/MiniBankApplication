package com.example.ui.presentation

import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.useCase.HandleErrorUseCase
import com.example.core_ui.viewModel.BaseViewModel
import com.example.data.domain.request.UpdateUserProfileRequestDO
import com.example.data.domain.useCases.GetUserProfileUseCase
import com.example.data.domain.useCases.UpdateUserProfileUseCase
import com.example.navigation.Navigator
import com.example.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
@HiltViewModel
class ProfileViewModel @Inject constructor(
    val updateUserProfileUseCase: UpdateUserProfileUseCase,
    val getProfileUseCase: GetUserProfileUseCase,
    //val authLocalDataStore: AuthLocalDataStore,
    val handleErrorUseCase: HandleErrorUseCase,
    val navigator: Navigator
) : BaseViewModel<ProfileContract.State, ProfileContract.Effect>(ProfileContract.State(), handleErrorUseCase) {

    init {
        getUserProfile()
    }


    fun handleIntent(intent: ProfileContract.Intent){
        when(intent){
            is ProfileContract.Intent.SetName -> {
                updateState { it.copy(name = intent.name) }
            }
            is ProfileContract.Intent.SetSurname -> {
                updateState { it.copy(surname = intent.surname) }
            }
            is ProfileContract.Intent.SetEmail -> {
                updateState { it.copy(email = intent.email) }
            }
            is ProfileContract.Intent.SetPhone -> {
                updateState { it.copy(phone = intent.phone) }
            }
            is ProfileContract.Intent.OnSaveClick -> {
                saveUserData()
            }
            is ProfileContract.Intent.OnNextClick -> {
                navigateToCardScreen(intent.route)
            }
        }

    }

    private fun getUserProfile(){
        updateState{it.copy(loading = true)}
        viewModelScope.launch {
            when(val res = getProfileUseCase()){
                is ResultWrapper.Success -> {
                    /*authLocalDataStore.saveUserName(res.data.firstName)
                    authLocalDataStore.saveSurname(res.data.lastName)
                    authLocalDataStore.savePhone(res.data.phoneNumber)
                    authLocalDataStore.saveEmail(res.data.email)*/
                    updateState { it.copy(
                        loading = false ,
                        name = res.data.firstName,
                        surname = res.data.lastName,
                        phone = res.data.phoneNumber,
                        email = res.data.email,
                        userProfile = res.data
                    ) }
                }
                is ResultWrapper.Error -> {
                    updateState { it.copy(loading = false) }
                    handleError(res.error)

                }
            }

        }
    }

    private fun saveUserData(){
        val currentState = currentState()
        if(!currentState.hasUnsavedChanges) return
        viewModelScope.launch {
            updateState { it.copy(loading = true) }
            val res = updateUserProfileUseCase(
                UpdateUserProfileRequestDO(
                    firstName = currentState.name,
                    lastName = currentState.surname,
                    phoneNumber = currentState.phone,
                    email = currentState.email
                )
            )
            when(res){
                is ResultWrapper.Success -> {
                    updateState { it.copy(loading = false) }
                    updateState { it.copy(userProfile = res.data) }
                }
                is ResultWrapper.Error -> {
                    updateState { it.copy(loading = false) }
                    handleError(error = res.error)
                }
            }

        }
    }

    private fun navigateToCardScreen(route : Route){
        navigator.navigate(route)
    }

    override fun showMessage(message: Int) {
        viewModelScope.launch {
            sendEffect(ProfileContract.Effect.ShowErrorMessage(message))
        }
    }
}