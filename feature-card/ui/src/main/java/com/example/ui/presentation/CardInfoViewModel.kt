package com.example.ui.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.ResultWrapper
import com.example.core_ui.util.BusinessErrorTypeEnum
import com.example.data.domain.request.BalancesRequestDO
import com.example.data.domain.response.CardWithBalanceDO
import com.example.data.domain.useCases.GetBalancesUseCase
import com.example.data.domain.useCases.GetCardUseCase
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

@HiltViewModel
class CardInfoViewModel @Inject constructor(
    val getBalancesUseCase: GetBalancesUseCase,
    val getCardUseCase: GetCardUseCase,
    val navigator: Navigator
) : ViewModel() {

    var maskedPans : List<String> = emptyList()
    private val _state = MutableStateFlow(CardInfoContract.State())
    val state  = _state.asStateFlow()

    private val _effect = MutableSharedFlow<CardInfoContract.Effect>()
    val effect = _effect.asSharedFlow()


    init {
       loadCards()
    }

    fun handleIntent(intent : CardInfoContract.Intent){
        when(intent){
            is CardInfoContract.Intent.OnCardChange -> {
               _state.update { it.copy(cardIndex = intent.index) }
            }
            is CardInfoContract.Intent.OnNavigateTransaction -> {
                 navigateToTransactions(intent.route)
            }
            is CardInfoContract.Intent.OnNavigateBalanceTransfer -> {
                navigateToTransferScreen(intent.route)

            }

        }
    }

    private fun loadCards(){
        viewModelScope.launch {
            _state.update { it.copy(isCardSectionLoading = true) }
            when(val res = getCardUseCase()){
                is ResultWrapper.Success -> {
                   maskedPans=  res.data.map{ it.maskedPan }
                    res.data.forEach {
                        Log.e("kard num", it.maskedPan)
                    }
                    _state.update { it.copy(isCardSectionLoading = false ,cardList = res.data) }
                       loadBalances()
                }
                is ResultWrapper.Error -> {
                    _state.update{ it.copy(isCardSectionLoading = false)}
                    val enumType = BusinessErrorTypeEnum.getBusinessError(res.code)
                    val message = enumType.errorMessage
                    _effect.emit(CardInfoContract.Effect.ShowErrorMessage(message))
                }
            }
        }
    }

    private fun loadBalances() {
        viewModelScope.launch {
            _state.update { it.copy(isBalanceSectionLoading = true) }
            if(maskedPans.isEmpty()) return@launch

            when(val res = getBalancesUseCase(balancesRequestDO = BalancesRequestDO(
                maskedPans = maskedPans
            )
            )){
                is ResultWrapper.Success -> {
                   val balances =  res.data
                    val balancesWithKey = balances.associateBy { it.maskedPan }
                     val combined = _state.value.cardList.map { card->
                         CardWithBalanceDO(
                             cardDO = card,
                             balanceDO = balancesWithKey[card.maskedPan]
                         )
                   }
                    _state.update { it.copy(cardWithBalanceList = combined, isBalanceSectionLoading = false) }
                }
                is ResultWrapper.Error -> {
                    _state.update{ it.copy(isBalanceSectionLoading = false)}
                    val enumType = BusinessErrorTypeEnum.getBusinessError(res.code)
                    val message = enumType.errorMessage
                    _effect.emit(CardInfoContract.Effect.ShowErrorMessage(message))
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


}