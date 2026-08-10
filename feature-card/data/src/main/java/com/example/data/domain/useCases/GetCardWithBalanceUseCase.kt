package com.example.data.domain.useCases

import com.example.core.domain.model.AppError
import com.example.core.domain.model.ResultWrapper
import com.example.data.domain.request.BalancesRequestDO
import com.example.data.domain.response.BalanceDO
import com.example.data.domain.response.CardDO
import com.example.data.domain.response.CardWithBalanceDO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCardWithBalanceUseCase @Inject constructor(
    val getCardUseCase: GetCardUseCase,
    val getBalancesUseCase: GetBalancesUseCase
) {

    operator fun invoke(): Flow<ResultWrapper<List<CardWithBalanceDO>>> = flow {

        when (val cardResult = getCardUseCase()) {

            is ResultWrapper.Error -> {
                emit(ResultWrapper.Error(error = cardResult.error))
            }

            is ResultWrapper.Success -> {
                val cardList = cardResult.data
                emit(
                    ResultWrapper.Success(
                        data = cardList.map { card ->
                            CardWithBalanceDO(cardDO = card, balanceDO = null)
                        }
                    )
                )

                if (cardList.isEmpty()) {
                    return@flow
                }

                val maskedPanList = cardList.map { it.maskedPan }

                when (val balancesResult = getBalancesUseCase(BalancesRequestDO(maskedPanList))) {

                    is ResultWrapper.Success -> {
                        val balanceWithKey = balancesResult.data.associateBy { it.maskedPan }

                        val result = cardList.map { card ->
                            val balance = balanceWithKey[card.maskedPan]
                            CardWithBalanceDO(
                                cardDO = card,
                                balanceDO = balance?.let {
                                    ResultWrapper.Success(balance)
                                }
                            )
                        }

                        emit(ResultWrapper.Success(result))
                    }

                    is ResultWrapper.Error -> {
                        val result = cardList.map { card ->
                            CardWithBalanceDO(
                                cardDO = card,
                                balanceDO = ResultWrapper.Error(error = balancesResult.error)
                            )
                        }

                        emit(ResultWrapper.Success(result))
                    }
                }
            }
        }
    }
}

sealed interface CardBalanceUiState {
    data object Idle  : CardBalanceUiState
    data object Loading : CardBalanceUiState
    data class Success( val data : BalanceDO) : CardBalanceUiState
    data class Error (val error : AppError) : CardBalanceUiState
}


 fun ResultWrapper<BalanceDO>?.toBalanceUiState(): CardBalanceUiState {
    return when (this) {
        null -> CardBalanceUiState.Idle
        is ResultWrapper.Success -> CardBalanceUiState.Success(this.data)
        is ResultWrapper.Error -> CardBalanceUiState.Error(this.error)
    }
}

data class CardWithBalanceUiModel(
    val cardDO: CardDO,
    val balanceUiState: CardBalanceUiState
)




