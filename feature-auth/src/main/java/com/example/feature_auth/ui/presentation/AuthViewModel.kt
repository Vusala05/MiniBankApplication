package com.example.feature_auth.ui.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.useCase.HandleErrorUseCase
import com.example.core_ui.viewModel.BaseViewModel
import com.example.feature_auth.data.dataSource.AuthLocalDataSource
import com.example.feature_auth.domain.request.UpdateUserProfileRequestDO
import com.example.feature_auth.domain.useCases.GetUserProfileUseCase
import com.example.feature_auth.domain.useCases.UpdateUserProfileUseCase
import com.example.navigation.Navigator
import com.example.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO -> BaseViewModel, BaseUiState ve BaseUiEffect(erroru handle et)
@HiltViewModel
class AuthViewModel @Inject constructor(
    val updateUserProfileUseCase: UpdateUserProfileUseCase,
    val getProfileUseCase: GetUserProfileUseCase,
    val authLocalDataSource: AuthLocalDataSource,
    val handleErrorUseCase: HandleErrorUseCase,
    val navigator: Navigator
) : BaseViewModel<AuthContract.State, AuthContract.Effect>(AuthContract.State(), handleErrorUseCase) {

    init {
        getUserProfile()
    }


    fun handleIntent(intent: AuthContract.Intent){
        when(intent){
            is AuthContract.Intent.SetName -> {
                updateState { it.copy(name = intent.name) }
            }
            is AuthContract.Intent.SetSurname -> {
                updateState { it.copy(surname = intent.surname) }
            }
            is AuthContract.Intent.SetEmail -> {
               updateState { it.copy(email = intent.email) }
            }
            is AuthContract.Intent.SetPhone -> {
                updateState { it.copy(phone = intent.phone) }
            }
            is AuthContract.Intent.OnSaveClick -> {
                saveUserData()
            }
            is AuthContract.Intent.OnNextClick -> {
                navigateToCardScreen(intent.route)
            }
        }

    }

    private fun getUserProfile(){
        updateState{it.copy(loading = true)}
        viewModelScope.launch {
            when(val res = getProfileUseCase()){
                is ResultWrapper.Success -> {
                    authLocalDataSource.saveUserName(res.data.firstName)
                    authLocalDataSource.saveSurname(res.data.lastName)
                    authLocalDataSource.savePhone(res.data.phoneNumber)
                    authLocalDataSource.saveEmail(res.data.email)
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
            sendEffect(AuthContract.Effect.ShowErrorMessage(message))
        }
    }
}