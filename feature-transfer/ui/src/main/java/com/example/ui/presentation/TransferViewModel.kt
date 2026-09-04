package com.example.ui.presentation

import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.useCase.HandleErrorUseCase
import com.example.core_ui.viewModel.BaseViewModel
import com.example.data.domain.request.CalculateCommissionRequestDO
import com.example.data.domain.request.TransferRequestDO
import com.example.data.domain.useCases.CalculateCommissionUseCase
import com.example.data.domain.useCases.ExecuteTransferUseCase
import com.example.data.domain.useCases.GetCardUseCase
import com.example.navigation.Navigator
import com.example.navigation.Route
import com.example.ui.util.CardSelectionType
import com.example.ui.util.TransferType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@HiltViewModel
class TransferViewModel @Inject constructor(
    val calculateCommissionUseCase: CalculateCommissionUseCase,
    val executeTransferUseCase: ExecuteTransferUseCase,
    val handleErrorUseCase: HandleErrorUseCase,
    val getCardUseCase: GetCardUseCase,
    val navigator: Navigator
) : BaseViewModel<TransferContract.State, TransferContract.Effect>(TransferContract.State(), handleErrorUseCase) {

    init {
        handleCalculateCommission()
    }


    fun handleIntent(intent: TransferContract.Intent) {
        when (intent) {
            is TransferContract.Intent.AmountChange -> {
                updateState { it.copy(amount = intent.amount) }
            }

            is TransferContract.Intent.SourceCardChange -> {
                updateState {
                    it.copy(
                        sourceCard = intent.sourceCard,
                        commissionPreviewResponse = null,
                        expandedBottomSheet = false,
                        errorCode = null
                    )
                }

            }

            is TransferContract.Intent.DestinationCardChange -> {
                updateState{
                    it.copy(
                        destinationCard = intent.destinationCard,
                        expandedBottomSheet = false,
                        commissionPreviewResponse = null,
                        errorCode = null
                    )
                }

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


            is TransferContract.Intent.SelectCard -> {
                val currentState = currentState()
                val selectedCard = currentState.cardList.find { it.id == intent.card.id }
                updateState { it.copy(currency = selectedCard?.currency ?: it.currency) }
                updateState { current ->
                    when (current.cardSelectionType) {
                        CardSelectionType.SOURCE_CARD_ID -> current.copy(sourceCard = intent.card)
                        CardSelectionType.DESTINATION_CARD_ID -> current.copy(destinationCard = intent.card)
                        CardSelectionType.NONE -> current
                    }
                }

            }
            is TransferContract.Intent.DestinationPanChanged -> {
                  updateState { it.copy(destinationPan = intent.destinationPanId)}
            }

            is TransferContract.Intent.GetTransferType -> {
                   updateState { it.copy(transferType = intent.transferType, amount = "",
                       destinationPan = null,
                       destinationCard = null,
                       sourceCard = null,
                       commissionPreviewResponse = null)}
            }

            is TransferContract.Intent.GetCallBackUrlParams -> {
                val transactionId = intent.transactionId
                val status = intent.status
                if(transactionId!=null && status!=null){
                    updateState { it.copy(threeDSUrl = null,transactionId = transactionId, status = status) }
                }
            }

            is TransferContract.Intent.OnNavigateScreen ->{
                onNavigate(intent.route)
            }


        }

    }


    private fun handleCalculateCommission() {
        viewModelScope.launch {
            state.distinctUntilChanged { old, new ->
                val amountSame = old.amount == new.amount
                val sourceCardSame = old.sourceCard == new.sourceCard
               val transferTypeSame = old.transferType == new.transferType

                val destinationSame = when (new.transferType) {
                    TransferType.BETWEEN_OWN_CARD -> old.destinationCard == new.destinationCard
                    TransferType.BETWEEN_OTHER_CARD -> old.destinationPan == new.destinationPan
                }

                amountSame && sourceCardSame  && transferTypeSame && destinationSame
            }.debounce(300)
                .collectLatest {
                    val destinationValid = when (it.transferType) {
                        TransferType.BETWEEN_OWN_CARD -> it.destinationCard != null
                        TransferType.BETWEEN_OTHER_CARD -> !it.destinationPan.isNullOrBlank() && it.destinationPan.length==16
                    }

                    if (it.amount.isNotBlank() && it.sourceCard != null && destinationValid) {
                        calculateCommission(it.amount)
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
                    sourceCardId = currentState.sourceCard?.id ?: "",
                    destinationCardId = if (currentState.transferType == TransferType.BETWEEN_OWN_CARD) currentState.destinationCard?.id else null,
                    destinationPan = if (currentState.transferType == TransferType.BETWEEN_OTHER_CARD) currentState.destinationPan else null,
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
                    sourceCardId = currentState.sourceCard?.id ?: "",
                    destinationCardId = if (currentState.transferType == TransferType.BETWEEN_OWN_CARD) currentState.destinationCard?.id else null,
                    destinationPan = if (currentState.transferType == TransferType.BETWEEN_OTHER_CARD) currentState.destinationPan else null,
                    amount = currentState.amount,
                    currency = currentState.currency
                )
            )) {
                is ResultWrapper.Success -> {
                    updateState { it.copy(isLoading = false, transferResult = res.data) }
                    if(res.data.requires3DS){
                    updateState { it.copy(threeDSUrl = res.data.threeDSUrl?.replace("localhost:8080", "10.0.2.2:9090"), callbackUrl = res.data.callbackUrl) }
                    }
                    else{
                        updateState { it.copy(ownCardTransferStatus = "SUCCESS")}
                    }
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
    private fun onNavigate(route: Route){
        navigator.navigate(route)
    }

    override fun showMessage(message: Int) {
        viewModelScope.launch {
            sendEffect(TransferContract.Effect.ShowMessage(message))
        }
    }
}