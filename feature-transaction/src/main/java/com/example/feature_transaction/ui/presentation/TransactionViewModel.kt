package com.example.feature_transaction.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.ResultWrapper
import com.example.core_ui.util.BusinessErrorTypeEnum
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
    val getTransactionUseCase: GetTransactionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionContract.State())
    val state  = _state.asStateFlow()

    private val _effect = MutableSharedFlow<TransactionContract.Effect>()
    val effect = _effect.asSharedFlow()

    init {
      loadTransactions()
    }

    fun handleIntent(intent: TransactionContract.Intent){

    }

    private fun loadTransactions(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when(val res = getTransactionUseCase()){
                is ResultWrapper.Success -> {
                      _state.update { it.copy(isLoading = false, transactionList = res.data) }
                }
                is ResultWrapper.Error -> {
                    _state.update { it.copy(isLoading = false) }
                    val enumType = BusinessErrorTypeEnum.getBusinessError(res.code)
                    val message = enumType.errorMessage
                    _effect.emit(TransactionContract.Effect.ShowErrorMessage(message))
                }
            }
        }

    }

}