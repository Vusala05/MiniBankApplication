package com.example.feature_auth.ui.presentation.verifiedPin

import com.example.core.domain.useCase.HandleErrorUseCase
import com.example.core_ui.viewModel.BaseViewModel
import com.example.feature_auth.domain.useCases.CheckingPinUseCase
import com.example.feature_auth.ui.presentation.AuthContract
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class VerifiedPinViewModel @Inject constructor(
    val checkingPinUseCase: CheckingPinUseCase,
    val handleErrorUseCase: HandleErrorUseCase
)  : BaseViewModel<AuthContract.State, AuthContract.Effect>(
    initialState = AuthContract.State(),
    handleErrorUseCase = handleErrorUseCase
) {


    override fun showMessage(message: Int) {

    }

}