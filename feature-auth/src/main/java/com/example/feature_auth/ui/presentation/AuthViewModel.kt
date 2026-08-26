/*
package com.example.feature_auth.ui.presentation

import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.useCase.HandleErrorUseCase
import com.example.core_ui.viewModel.BaseViewModel
import com.example.feature_auth.data.dataSource.AuthLocalDataSource
import com.example.feature_auth.domain.request.RefreshTokenRequestDO
import com.example.feature_auth.domain.useCases.CheckingPinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    val checkingPinUseCase: CheckingPinUseCase,
    val handleErrorUseCase: HandleErrorUseCase,
    val authLocalDataSource: AuthLocalDataSource
)  : BaseViewModel<AuthContract.State, AuthContract.Effect>(
    initialState = AuthContract.State(),
    handleErrorUseCase = handleErrorUseCase
) {

    fun handleIntent(intent: AuthContract.Intent) {
        when(intent){
            is AuthContract.Intent.OnPinChange -> {
                 onPinChange(intent.newPin)
            }
            is AuthContract.Intent.NavigateProfileScreen ->  {

            }
        }
    }

    private fun onPinChange(newPin : String){
        val currentState = currentState()
        when(currentState.pinStep){
             AuthContract.PinStep.INITIAL -> {
                 if(newPin.length < 5 && newPin.all { it.isDigit() }){
                     updateState { it.copy(initialPin = newPin, showPinError = false ) }
                 }
                 if(newPin.length == 4){
                     viewModelScope.launch {
                         delay(2000)
                         when(val res = checkingPinUseCase(RefreshTokenRequestDO(
                             refreshToken = authLocalDataSource.getRefreshToken(),
                             pin = newPin
                             )))
                         {
                             is ResultWrapper.Success -> {
                                 updateState { it.copy(pinStep = AuthContract.PinStep.VERIFIED, showPinError = false) }
                             }

                             is ResultWrapper.Error -> {
                                 updateState { it.copy(initialPin = "", showPinError = true) }
                                 handleError(res.error)
                             }

                         }
                     }

                 }
             }

            AuthContract.PinStep.VERIFIED -> {


            }
            AuthContract.PinStep.SUCCESS -> {

            }

        }

    }



    override fun showMessage(message: Int) {
        viewModelScope.launch {
            sendEffect(AuthContract.Effect.ShowMessage(message))
        }
    }


}*/
