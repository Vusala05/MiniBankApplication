package com.example.feature_transfer.ui.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature_transfer.ui.components.BottomSheetIContent
import com.example.feature_transfer.ui.components.CardSelectorItem
import com.example.feature_transfer.ui.components.CommissionPreview
import com.example.feature_transfer.ui.components.InputField
import com.example.feature_transfer.ui.components.ResultStatusScreen
import com.example.feature_transfer.ui.components.TransferTabSwitcher
import com.example.feature_transfer.ui.util.CardSelectionType
import com.example.feature_transfer.ui.util.TransferType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(
    state : TransferContract.State,
    handleIntent : (TransferContract.Intent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Transfer Money",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF8F9FA)
                )
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                .padding(15.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            TransferTabSwitcher(
                selectedTabType = state.transferType,
                onTabSelected = {
                    handleIntent(TransferContract.Intent.GetTransferType(it))
                }
            )

            Spacer(modifier = Modifier.height(15.dp))

            AnimatedContent(
                targetState = state.transferType,
                transitionSpec = { fadeIn(tween(400)) togetherWith fadeOut(tween(200)) },
                label = "TabContentAnimation"
            ) { tab ->
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    when (tab) {
                        TransferType.BETWEEN_OWN_CARD -> {
                            CardSelectorItem(
                                label = "From",
                                selectedCard = state.sourceCard,
                                isSource = true,
                                onClick = { expanded, cardSelectionType ->
                                    handleIntent(TransferContract.Intent.OnClickCard(
                                        isExpanded = expanded,
                                        cardSelectionType = cardSelectionType
                                    ))
                                })

                            CardSelectorItem(
                                label = "To",
                                selectedCard = state.destinationCard,
                                isSource = false,
                                onClick = { expanded, cardSelectionType ->
                                    handleIntent(TransferContract.Intent.OnClickCard(
                                        isExpanded = expanded,
                                        cardSelectionType = cardSelectionType
                                    ))

                                })
                        }


                        TransferType.BETWEEN_OTHER_CARD -> {
                            CardSelectorItem(
                                label = "From",
                                selectedCard = state.sourceCard,
                                isSource = true,
                                onClick = { expanded, cardSelectionType ->
                                    handleIntent(TransferContract.Intent.OnClickCard(
                                        isExpanded = expanded,
                                        cardSelectionType = cardSelectionType
                                    ))

                                })
                            InputField(
                                value = state.destinationPan ?: "",
                                isTransfer = true,
                                title = "To",
                                placeHolder = "The Card Number",
                                onValueChange = {
                                    handleIntent(TransferContract.Intent.DestinationPanChanged(it))
                                }
                            )
                        }
                    }

                    Spacer(Modifier.height(15.dp))
                    InputField(
                        value = state.amount,
                        isTransfer = false,
                        title = "Amount",
                        placeHolder = "Amount",
                        onValueChange = {
                            handleIntent(TransferContract.Intent.AmountChange(it))
                        }
                    )
                    Spacer(Modifier.height(4.dp))

                    CommissionPreview(
                        state = state,
                        isLoading = state.checkingAvailabilityOfTransformation
                    )

                    Button(
                        onClick = { handleIntent(TransferContract.Intent.CheckComission) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (state.checkingAvailabilityOfTransformation) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = Color.White
                            )
                        } else {
                            Text("Checking Commission")
                        }
                    }
                    Button(
                        onClick = { handleIntent(TransferContract.Intent.OnSubmitClick) },
                        //enabled = state.isConfirmEnable,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = Color.White
                            )
                        } else {
                            Text("Confirm transfer")
                        }
                    }

                }
            }
            if (state.expandedBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        handleIntent(
                            TransferContract.Intent.OnClickCard(
                                isExpanded = false,
                                cardSelectionType = CardSelectionType.NONE
                            )
                        )
                    },
                    sheetState = sheetState
                ) {
                    BottomSheetIContent(
                        handleIntent = handleIntent,
                        state = state
                    )
                }
            }

        }

    }
    if(state.status!=null){
        Box(
            modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)){
            ResultStatusScreen(
                status = state.status,
                transactionId = state.transactionId ?:"",
                onDoneClick = {}
            )
        }
    }

}
