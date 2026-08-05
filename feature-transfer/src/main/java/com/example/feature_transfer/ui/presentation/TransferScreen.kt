package com.example.feature_transfer.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature_transfer.ui.components.BottomSheetIContent
import com.example.feature_transfer.ui.components.PreviewRow
import com.example.feature_transfer.ui.util.CardSelectionType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(
    state : TransferContract.State,
    handleIntent : (TransferContract.Intent) -> Unit
){
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .padding(20.dp)
    ) {
        Text(
            text = "Transfer money",
            fontSize = 15.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "From",
            fontSize = 13.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Button(
            onClick = { handleIntent(TransferContract.Intent.OnClickCard(isExpanded = true, cardSelectionType = CardSelectionType.SOURCE_CARD_ID)) },
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp)
        ) {
            Text("Select Source Code")
        }
        Text(
            text = "To",
            fontSize = 13.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Button(
            onClick = { handleIntent(TransferContract.Intent.OnClickCard(isExpanded = true,  cardSelectionType = CardSelectionType.DESTINATION_CARD_ID)) },
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp)
        ) {
            Text("Select Destination Code")
        }

        Text(
            text = "Amount",
            fontSize = 13.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = state.amount,
            onValueChange = { handleIntent(TransferContract.Intent.AmountChange(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            placeholder = { Text("0.00") }
        )

        HorizontalDivider()
        if(state.checkingAvailabilityOfTransformation){
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                CircularProgressIndicator(modifier = Modifier.size(36.dp), color = Color.Black)
            }
        }
        else {
            Column(modifier = Modifier.padding(top = 12.dp, bottom = 20.dp)) {
                val preview = state.commissionPreviewResponse

                PreviewRow(
                    label = "Amount",
                    value = preview?.amount?.plus(" ${state.currency}") ?: "—"
                )
                PreviewRow(
                    label = "Commission (${preview?.commissionRate ?: "—"})",
                    value = preview?.commissionAmount?.plus(" ${state.currency}") ?: "—"
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total debit",
                        fontSize = 14.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                    )

                    if (state.checkingAvailabilityOfTransformation) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = preview?.totalAmount?.plus(" ${state.currency}") ?: "—",
                            fontSize = 18.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                        )
                    }
                }
            }
        }
        Button(
            onClick = { handleIntent(TransferContract.Intent.CheckComission) },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.checkingAvailabilityOfTransformation) {
                CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp, color = Color.White)
            } else {
                Text("Checking Commission")
            }
        }
        Button(
            onClick = { handleIntent(TransferContract.Intent.OnSubmitClick) },
            enabled = state.isConfirmEnable,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp, color = Color.White)
            } else {
                Text("Confirm transfer")
            }
        }
    }
    if (state.expandedBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { handleIntent(TransferContract.Intent.OnClickCard(isExpanded = false, cardSelectionType = CardSelectionType.NONE)) },
            sheetState = sheetState
        ) {
            BottomSheetIContent(
                handleIntent = handleIntent,
                state = state
            )
        }
    }

}




