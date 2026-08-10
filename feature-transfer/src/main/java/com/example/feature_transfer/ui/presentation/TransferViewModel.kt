package com.example.feature_transfer.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.useCase.HandleErrorUseCase
import com.example.core_ui.R
import com.example.core_ui.viewModel.BaseViewModel
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
    val handleErrorUseCase: HandleErrorUseCase,
    val getCardUseCase: GetCardUseCase
) : BaseViewModel<TransferContract.State, TransferContract.Effect>(TransferContract.State(), handleErrorUseCase) {

    private val amountFlow = MutableStateFlow("")


    fun handleIntent(intent: TransferContract.Intent) {
        when (intent) {
            is TransferContract.Intent.AmountChange -> {
                updateState { it.copy(amount = intent.amount) }
                amountFlow.value = intent.amount
            }

            is TransferContract.Intent.SourceCardIdChange -> {
                updateState {
                    it.copy(
                        sourceCardId = intent.sourceCardId,
                        commissionPreviewResponse = null,
                        errorCode = null
                    )
                }

            }

            is TransferContract.Intent.DestinationCardIdChange -> {
                updateState{
                    it.copy(
                        destinationCardId = intent.destinationCardId,
                        commissionPreviewResponse = null,
                        errorCode = null
                    )
                }

            }

            is TransferContract.Intent.CurrencyChange -> {
                updateState { it.copy(currency = intent.currency) }

            }

            is TransferContract.Intent.OnSubmitClick -> {
                executeTransfer()
            }

            is TransferContract.Intent.OnClickCard -> {
                updateState {
                    it.copy(
                        expandedBottomSheet = intent.isExpanded,
                        cardSelectionType = intent.cardSelectionType
                    )
                }
                if (intent.isExpanded) {
                    getCardList()

                }
            }

            is TransferContract.Intent.CheckComission -> {
                viewModelScope.launch {
                    amountFlow
                        .debounce(300)
                        .distinctUntilChanged()
                        .collectLatest { amount ->
                            if (amount.isNotBlank())
                                calculateCommission(amount)
                        }
                }
            }

            is TransferContract.Intent.SelectCard -> {
                val currentState = currentState()
                val selectedCard = currentState.cardList.find { it.id == intent.cardId }
                updateState { it.copy(currency = selectedCard?.currency ?: it.currency) }
                updateState { current ->
                    when (current.cardSelectionType) {
                        CardSelectionType.SOURCE_CARD_ID -> current.copy(sourceCardId = intent.cardId)
                        CardSelectionType.DESTINATION_CARD_ID -> current.copy(destinationCardId = intent.cardId)
                        CardSelectionType.NONE -> current
                    }
                }

            }


        }

    }

    private fun calculateCommission(amount: String) {
        updateState {
            it.copy(
                commissionCheckingIsSuccessful = false,
                checkingAvailabilityOfTransformation = true,
                errorCode = null
            )
        }
        viewModelScope.launch {
            val currentState = currentState()
            when (val res = calculateCommissionUseCase(
                CalculateCommissionRequestDO(
                    sourceCardId = currentState.sourceCardId,
                    destinationCardId = currentState.destinationCardId,
                    amount = amount,
                    currency = currentState.currency
                )
            )) {
                is ResultWrapper.Success -> {
                   updateState {
                        it.copy(
                            commissionCheckingIsSuccessful = true,
                            checkingAvailabilityOfTransformation = false,
                            commissionPreviewResponse = res.data
                        )
                    }
                }

                is ResultWrapper.Error -> {
                    val error = handleErrorUseCase(res.error)
                    updateState {
                        it.copy(
                            commissionCheckingIsSuccessful = false,
                            checkingAvailabilityOfTransformation = false,
                            errorCode = error.errorCode
                        )
                    }
                    handleError(res.error)

                }
            }

        }
    }

    private fun executeTransfer() {
        viewModelScope.launch {
            val currentState = currentState()
            updateState { it.copy(isLoading = true) }
            when (val res = executeTransferUseCase(
                TransferRequestDO(
                    sourceCardId = currentState.sourceCardId,
                    destinationCardId = currentState.destinationCardId,
                    amount = currentState.amount,
                    currency = currentState.currency
                )
            )) {
                is ResultWrapper.Success -> {
                    updateState { it.copy(isLoading = false, transferResult = res.data) }
                   // showMessage(R.string.successfully_transfer)

                }

                is ResultWrapper.Error -> {
                    updateState { it.copy(isLoading = false) }
                    handleError(res.error)

                }
            }
        }
    }

    private fun getCardList() {
        viewModelScope.launch {
            when (val res = getCardUseCase()) {
                is ResultWrapper.Success -> {
                    updateState { it.copy(cardList = res.data) }
                }

                is ResultWrapper.Error -> {
                    val error = handleErrorUseCase(res.error)

                    updateState {
                        it.copy(
                            checkingAvailabilityOfTransformation = false,
                            errorCode = error.errorCode
                        )
                    }
                    handleError(res.error)
                }
            }

        }

    }

    override fun showMessage(message: Int) {
        viewModelScope.launch {
            sendEffect(TransferContract.Effect.ShowMessage(message))
        }
    }
}