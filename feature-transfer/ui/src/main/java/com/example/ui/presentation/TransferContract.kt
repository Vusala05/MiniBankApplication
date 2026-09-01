package com.example.ui.presentation

import com.example.data.domain.response.CardDO
import com.example.data.domain.response.CommissionPreviewResponseDO
import com.example.data.domain.response.TransferResponseDO
import com.example.navigation.Route
import com.example.ui.util.CardSelectionType
import com.example.ui.util.TransferType

object TransferContract {

    sealed interface Effect {
        data class ShowMessage(val message : Int) : Effect
    }

    sealed interface Intent{
        data class SourceCardChange(val sourceCard : CardDO) : Intent
        data class DestinationCardChange(val destinationCard : CardDO) : Intent
        data class AmountChange(val amount : String) : Intent
        data class CurrencyChange(val currency : String) : Intent
        data object OnSubmitClick : Intent
        data class OnNavigateScreen ( val route: Route ) : Intent
        data class SelectCard (val card : CardDO) : Intent

        data class OnClickCard (val isExpanded : Boolean, val cardSelectionType: CardSelectionType ) : Intent
        data class GetCallBackUrlParams (val transactionId : String?, val status : String?) : Intent
        data class GetTransferType ( val transferType: TransferType) : Intent
        data class DestinationPanChanged (val destinationPanId : String ) : Intent

    }

    data class State(
        val isLoading : Boolean = false,
        val sourceCard : CardDO?=null,
        val destinationCard : CardDO ?=null,
        val amount : String = "",
        val currency : String = "AZN",
        val commissionPreviewResponse: CommissionPreviewResponseDO? = null,
        val transferResult: TransferResponseDO? = null,
        val checkingAvailabilityOfTransformation : Boolean = false,
        val errorCode : Int? = null,
        val expandedBottomSheet : Boolean = false,
        val cardList: List<CardDO> = emptyList(),
        val commissionCheckingIsSuccessful : Boolean = false,
        val cardSelectionType: CardSelectionType = CardSelectionType.NONE,
        val threeDSUrl : String?=null,
        val callbackUrl : String?=null,
        val transactionId : String?=null,
        val status : String?=null,
        val transferType: TransferType = TransferType.BETWEEN_OWN_CARD,
        val destinationPan : String?=null,
        val ownCardTransferStatus : String?=null
    ){
       /* val isConfirmEnable
            get() = amount.isNotEmpty() &&
                    sourceCardId.isNotEmpty() &&
                    destinationCardId.isNotEmpty() &&
                    errorCode !in listOf(1002,1006) &&
                    !checkingAvailabilityOfTransformation &&
                    !isLoading &&
                    commissionCheckingIsSuccessful*/

    }


}