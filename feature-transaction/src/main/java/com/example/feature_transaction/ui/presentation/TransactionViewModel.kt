package com.example.feature_transaction.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.useCase.HandleErrorUseCase
import com.example.core_ui.viewModel.BaseViewModel
import com.example.feature_transaction.domain.useCases.GetTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    val getTransactionUseCase: GetTransactionUseCase,
    val handleErrorUseCase: HandleErrorUseCase
) : BaseViewModel<TransactionContract.State, TransactionContract.Effect>(TransactionContract.State(), handleErrorUseCase) {

    init {
      loadTransactions()
    }

    fun handleIntent(intent: TransactionContract.Intent){

    }

    private fun loadTransactions(){
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            when(val res = getTransactionUseCase()){
                is ResultWrapper.Success -> {
                     updateState{ it.copy(isLoading = false, transactionList = res.data) }
                }
                is ResultWrapper.Error -> {
                    updateState { it.copy(isLoading = false) }
                    handleError(res.error)
                }
            }
        }

    }

    override fun showMessage(message: Int) {
       viewModelScope.launch {
           sendEffect(TransactionContract.Effect.ShowErrorMessage(message))
       }
    }

}