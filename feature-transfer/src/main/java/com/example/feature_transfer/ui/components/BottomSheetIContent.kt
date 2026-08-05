package com.example.feature_transfer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature_transfer.ui.presentation.TransferContract
import com.example.feature_transfer.ui.util.CardSelectionType

@Composable
fun  BottomSheetIContent(
    handleIntent : (TransferContract.Intent) -> Unit,
    state : TransferContract.State
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Kartı seçin",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 16.dp, start = 4.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = state.cardList,
                key = { card -> card.id }
            ) { card ->
                val isSelected = when(state.cardSelectionType){
                    CardSelectionType.SOURCE_CARD_ID -> { card.id == state.sourceCardId}
                    CardSelectionType.DESTINATION_CARD_ID -> {card.id == state.destinationCardId}
                    CardSelectionType.NONE -> false
                }
                BottomSheetItem(
                  onCardClick = { handleIntent(TransferContract.Intent.SelectCard(it))},
                    isSelected = isSelected,
                    card = card
                )
            }
        }
    }
}