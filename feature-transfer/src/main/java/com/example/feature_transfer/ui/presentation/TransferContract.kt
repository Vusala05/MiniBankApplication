package com.example.feature_transfer.ui.presentation

import com.example.data.domain.response.CardDO
import com.example.feature_transfer.data.response.CommissionPreviewResponse
import com.example.feature_transfer.domain.response.CommissionPreviewResponseDO
import com.example.feature_transfer.domain.response.TransferResponseDO
import com.example.feature_transfer.ui.util.CardSelectionType

object TransferContract {

    sealed interface Effect {
        data class ShowMessage(val message : Int) : Effect
    }

    sealed interface Intent{
        data class SourceCardIdChange(val sourceCardId : String) : Intent
        data class DestinationCardIdChange(val destinationCardId : String) : Intent
        data class AmountChange(val amount : String) : Intent
        data class CurrencyChange(val currency : String) : Intent
        data object OnSubmitClick : Intent
        data object CheckComission : Intent

        data class SelectCard (val cardId : String) : Intent

        data class OnClickCard (val isExpanded : Boolean, val cardSelectionType: CardSelectionType ) : Intent

    }

    data class State(
        val isLoading : Boolean = false,
        val sourceCardId : String = "",
        val destinationCardId : String = "",
        val amount : String = "",
        val currency : String = "AZN",
        val commissionPreviewResponse: CommissionPreviewResponseDO? = null,
        val transferResult: TransferResponseDO? = null,
        val checkingAvailabilityOfTransformation : Boolean = false,
        val errorCode : Int? = null,
        val expandedBottomSheet : Boolean = false,
        val cardList: List<CardDO> = emptyList(),
        val commissionCheckingIsSuccessful : Boolean = false,
        val cardSelectionType: CardSelectionType = CardSelectionType.NONE
    ){
        val isConfirmEnable
            get() = amount.isNotEmpty() &&
                    sourceCardId.isNotEmpty() &&
                    destinationCardId.isNotEmpty() &&
                    errorCode !in listOf(1002,1006) &&
                    !checkingAvailabilityOfTransformation &&
                    !isLoading &&
                    commissionCheckingIsSuccessful

    }
}