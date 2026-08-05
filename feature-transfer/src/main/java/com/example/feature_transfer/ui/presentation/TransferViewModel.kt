package com.example.feature_transfer.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.ResultWrapper
import com.example.core_ui.R
import com.example.core_ui.util.BusinessErrorTypeEnum
import com.example.data.domain.useCases.GetCardUseCase
import com.example.feature_transfer.domain.request.CalculateCommissionRequestDO
import com.example.feature_transfer.domain.request.TransferRequestDO
import com.example.feature_transfer.domain.useCases.CalculateCommissionUseCase
import com.example.feature_transfer.domain.useCases.ExecuteTransferUseCase
import com.example.feature_transfer.ui.util.CardSelectionType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TransferViewModel @Inject constructor(
    val calculateCommissionUseCase: CalculateCommissionUseCase,
    val executeTransferUseCase: ExecuteTransferUseCase,
    val getCardUseCase: GetCardUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TransferContract.State())
    val state  = _state.asStateFlow()

    private val _effect = MutableSharedFlow<TransferContract.Effect>()
    val effect = _effect.asSharedFlow()

    private val amountFlow  = MutableStateFlow("")
    init {

    }
    fun handleIntent(intent: TransferContract.Intent){
    when(intent){
        is TransferContract.Intent.AmountChange -> {
            _state.update { it.copy(amount = intent.amount) }
            amountFlow.value = intent.amount
        }
        is TransferContract.Intent.SourceCardIdChange -> {
            _state.update { it.copy(sourceCardId = intent.sourceCardId, commissionPreviewResponse = null, errorCode = null) }

        }
        is TransferContract.Intent.DestinationCardIdChange -> {
            _state.update { it.copy(destinationCardId = intent.destinationCardId, commissionPreviewResponse = null, errorCode = null) }

        }
        is TransferContract.Intent.CurrencyChange -> {
            _state.update { it.copy(currency = intent.currency) }

        }
        is TransferContract.Intent.OnSubmitClick -> {
            executeTransfer()
        }

        is TransferContract.Intent.OnClickCard -> {
            _state.update { it.copy(expandedBottomSheet = intent.isExpanded, cardSelectionType = intent.cardSelectionType) }
            if(intent.isExpanded){
                getCardList()

            }
        }
        is TransferContract.Intent.CheckComission ->{
            viewModelScope.launch {
                amountFlow
                    .debounce(300)
                    .distinctUntilChanged()
                    .collectLatest { amount ->
                        if(amount.isNotBlank())
                            calculateCommission(amount)
                    }
            }
        }

        is TransferContract.Intent.SelectCard -> {
            val selectedCard = _state.value.cardList.find { it.id == intent.cardId }
            _state.update {it.copy(currency = selectedCard?.currency ?: it.currency)}
            _state.update { current ->
                when (current.cardSelectionType) {
                    CardSelectionType.SOURCE_CARD_ID -> current.copy(sourceCardId = intent.cardId)
                    CardSelectionType.DESTINATION_CARD_ID -> current.copy(destinationCardId = intent.cardId)
                    CardSelectionType.NONE -> current
                }
            }

        }



    }

    }
    private fun calculateCommission(amount : String) {
        _state.update { it.copy(commissionCheckingIsSuccessful = false, checkingAvailabilityOfTransformation = true, errorCode = null) }
        viewModelScope.launch {
            val currentState = _state.value
            when (val res = calculateCommissionUseCase(
                CalculateCommissionRequestDO(
                 sourceCardId = currentState.sourceCardId,
                    destinationCardId = currentState.destinationCardId,
                    amount = amount,
                    currency = currentState.currency
                )
            )){
                is ResultWrapper.Success -> {
                    _state.update{ it.copy(commissionCheckingIsSuccessful = true,checkingAvailabilityOfTransformation = false, commissionPreviewResponse = res.data)}
                }
                is ResultWrapper.Error -> {
                    _state.update { it.copy(commissionCheckingIsSuccessful = false, checkingAvailabilityOfTransformation = false, errorCode = res.code) }
                    val enumType = BusinessErrorTypeEnum.getBusinessError(res.code)
                    val message = enumType.errorMessage
                    _effect.emit(TransferContract.Effect.ShowMessage(message))

                }
            }

        }
    }

    private fun executeTransfer(){
       viewModelScope.launch {
           val currentState = _state.value
           _state.update { it.copy(isLoading = true) }
           when(val res = executeTransferUseCase(
               TransferRequestDO(
                   sourceCardId = currentState.sourceCardId,
                   destinationCardId = currentState.destinationCardId,
                   amount = currentState.amount,
                   currency = currentState.currency
               )
           )){
               is ResultWrapper.Success -> {
                   _state.update { it.copy(isLoading = false, transferResult = res.data) }
                   _effect.emit(TransferContract.Effect.ShowMessage(R.string.successfully_transfer))

               }
               is ResultWrapper.Error -> {
                   _state.update { it.copy(isLoading = false) }
                   val enumType = BusinessErrorTypeEnum.getBusinessError(res.code)
                   val message = enumType.errorMessage
                   _effect.emit(TransferContract.Effect.ShowMessage(message))
               }
           }
       }
    }

    private fun getCardList() {
        viewModelScope.launch {
            when (val res = getCardUseCase()) {
                is ResultWrapper.Success -> {
                 _state.update { it.copy(cardList = res.data)}
                }

                is ResultWrapper.Error -> {
                    _state.update { it.copy(checkingAvailabilityOfTransformation = false, errorCode = res.code) }
                    val enumType = BusinessErrorTypeEnum.getBusinessError(res.code)
                    val message = enumType.errorMessage
                    _effect.emit(TransferContract.Effect.ShowMessage(message))
                }
            }
        }

    }

}