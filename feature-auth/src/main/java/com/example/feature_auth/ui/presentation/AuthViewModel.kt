package com.example.feature_auth.ui.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.ErrorModelDo
import com.example.core.domain.model.ResultWrapper
import com.example.core_ui.util.BusinessErrorTypeEnum
import com.example.core_ui.util.SystemErrorTypeEnum
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

sealed interface HandleErrorResult {
    data object Skip : HandleErrorResult
    data class Display (val error: ErrorModelDo) : HandleErrorResult
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    val updateUserProfileUseCase: UpdateUserProfileUseCase,
    val getProfileUseCase: GetUserProfileUseCase,
    val authLocalDataSource: AuthLocalDataSource,
    val navigator: Navigator
) : ViewModel() {

    private val _state = MutableStateFlow(AuthContract.State())
    val state  = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AuthContract.Effect>()
    val effect = _effect.asSharedFlow()

    init {
        getUserProfile()
        Log.e("inside init", "inside init")
    }


    fun handleIntent(intent: AuthContract.Intent){
        when(intent){
            is AuthContract.Intent.SetName -> {
                _state.update { it.copy(name = intent.name) }
            }
            is AuthContract.Intent.SetSurname -> {
                _state.update { it.copy(surname = intent.surname) }
            }
            is AuthContract.Intent.SetEmail -> {
                _state.update { it.copy(email = intent.email) }
            }
            is AuthContract.Intent.SetPhone -> {
                _state.update { it.copy(phone = intent.phone) }
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
        _state.update {it.copy(loading = true)}
        viewModelScope.launch {
            when(val res = getProfileUseCase()){
                is ResultWrapper.Success -> {
                    authLocalDataSource.saveUserName(res.data.firstName)
                    authLocalDataSource.saveSurname(res.data.lastName)
                    authLocalDataSource.savePhone(res.data.phoneNumber)
                    authLocalDataSource.saveEmail(res.data.email)
                    Log.e("inside init", "inside init success")

                    _state.update { it.copy(
                        loading = false ,
                        name = res.data.firstName,
                        surname = res.data.lastName,
                        phone = res.data.phoneNumber,
                        email = res.data.email,
                        userProfile = res.data
                    ) }
                }
                is ResultWrapper.Error -> {
                    Log.e("inside init", "inside init error")
                    Log.e("inside init", "${res.code} ${res.message} ${res.exception?.message} ")
                   _state.update { it.copy(loading = false) }
                    val enumType = BusinessErrorTypeEnum.getBusinessError(res.code)
                    val message = enumType.errorMessage
                    _effect.emit(AuthContract.Effect.ShowErrorMessage(message))

                }
            }

        }
    }

   private fun saveUserData(){
            val currentState = _state.value
            if(!currentState.hasUnsavedChanges) return
            viewModelScope.launch {
                _state.update { it.copy(loading = true) }
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
                        _state.update { it.copy(loading = false) }
                        _state.update { it.copy(userProfile = res.data) }
                    }
                    is ResultWrapper.Error -> {
                        _state.update { it.copy(loading = false) }
                        if (SystemErrorTypeEnum.isSystemError(res.code)) return@launch

                        val enumType = BusinessErrorTypeEnum.getBusinessError(res.code)
                        val message = enumType.errorMessage
                        _effect.emit(AuthContract.Effect.ShowErrorMessage(message))

                    }
                }

            }
    }

    private fun navigateToCardScreen(route : Route){
     navigator.navigate(route)
    }
}