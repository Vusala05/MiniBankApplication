package com.example.feature_auth.ui.presentation.InitialPin

import androidx.lifecycle.viewModelScope
import com.example.core.domain.useCase.HandleErrorUseCase
import com.example.core_ui.viewModel.BaseViewModel
import com.example.feature_auth.data.dataSource.AuthLocalDataSource
import com.example.feature_auth.domain.request.RefreshTokenRequestDO
import com.example.feature_auth.domain.useCases.CheckingPinUseCase
import com.example.feature_auth.ui.presentation.AuthContract
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class InitialPinViewModel @Inject constructor(
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
                //onPinChange(intent.newPin)
            }
            is AuthContract.Intent.NavigateProfileScreen ->  {

            }
        }
    }

    /*private fun onPinChange(newPin : String){
        viewModelScope.launch {
            when(val res = checkingPinUseCase(RefreshTokenRequestDO(
              refreshToken = authLocalDataSource.getRefreshToken(),
                pin =
            ))){

            }
        }

    }*/


    override fun showMessage(message: Int) {

    }

}