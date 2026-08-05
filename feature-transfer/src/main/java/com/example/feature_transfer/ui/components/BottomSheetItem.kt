package com.example.feature_transfer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.domain.response.CardDO
import com.example.feature_card.data.util.CardStatus

@Composable
fun BottomSheetItem(
    onCardClick : (String) -> Unit,
    isSelected : Boolean,
    card : CardDO,
    modifier: Modifier = Modifier
) {
    val cardColor = when(card.status){
        CardStatus.ACTIVE -> Color(0xFF327F4E)
        CardStatus.BLOCKED -> Color(0xFF944538)
        CardStatus.EXPIRED -> Color.Gray
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick(card.id) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor,

        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                if (card.cardholderName.isNotBlank()) {
                    Text(
                        text = card.cardholderName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = card.maskedPan,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = card.currency,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )

                }
            }

            RadioButton(
                selected = isSelected,
                onClick = { onCardClick(card.id) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Red
                )
            )
        }
    }
}
