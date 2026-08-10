package com.example.ui.presentation

import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.ResultWrapper
import com.example.core.domain.useCase.HandleErrorUseCase
import com.example.core_ui.viewModel.BaseViewModel
import com.example.data.domain.useCases.CardBalanceUiState
import com.example.data.domain.useCases.CardWithBalanceUiModel
import com.example.data.domain.useCases.GetCardWithBalanceUseCase
import com.example.data.domain.useCases.toBalanceUiState
import com.example.navigation.Navigator
import com.example.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardInfoViewModel @Inject constructor(
    val handleErrorUseCase: HandleErrorUseCase,
    val getCardWithBalanceUseCase: GetCardWithBalanceUseCase,
    val navigator: Navigator
) : BaseViewModel<CardInfoContract.State, CardInfoContract.Effect>(CardInfoContract.State(), handleErrorUseCase) {

    private var loadJob : Job?=null
    init {
       loadCardsWithBalances()
    }

    fun handleIntent(intent : CardInfoContract.Intent){
        when(intent){
            is CardInfoContract.Intent.OnCardChange -> {
               updateState { it.copy(cardIndex = intent.index) }
            }
            is CardInfoContract.Intent.OnNavigateTransaction -> {
                 navigateToTransactions(intent.route)
            }
            is CardInfoContract.Intent.OnNavigateBalanceTransfer -> {
                navigateToTransferScreen(intent.route)

            }

        }
    }
    private fun loadCardsWithBalances() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {

            updateState {
                it.copy(isCardSectionLoading = true, isBalanceSectionLoading = true)
            }

            getCardWithBalanceUseCase().collect { result ->
                when (result) {

                    is ResultWrapper.Success -> {
                        val uiList = result.data.map { item ->
                            CardWithBalanceUiModel(
                                cardDO = item.cardDO,
                                balanceUiState = item.balanceDO.toBalanceUiState()
                            )
                        }

                        val balancesArrived = uiList.none { it.balanceUiState is CardBalanceUiState.Idle }

                        updateState {
                            it.copy(
                                isCardSectionLoading = false,
                                isBalanceSectionLoading = !balancesArrived,
                                cardWithBalanceList = uiList
                            )
                        }
                    }

                    is ResultWrapper.Error -> {
                        updateState {
                            it.copy(isCardSectionLoading = false, isBalanceSectionLoading = false)
                        }
                       handleError(result.error)
                    }
                }
            }
        }
    }
    private fun navigateToTransactions(route: Route){
        navigator.navigate(route)
    }
    private fun navigateToTransferScreen(route: Route){
        navigator.navigate(route)
    }

    override fun showMessage(message: Int) {
      viewModelScope.launch {
          sendEffect(CardInfoContract.Effect.ShowErrorMessage(message))
      }
    }


}