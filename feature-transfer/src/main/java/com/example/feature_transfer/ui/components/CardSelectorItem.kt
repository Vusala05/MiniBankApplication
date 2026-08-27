package com.example.feature_transfer.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.data.util.CardType
import com.example.data.domain.response.CardDO
import com.example.feature_transfer.ui.util.CardSelectionType

@Composable
fun CardSelectorItem(
    isSource : Boolean,
    label: String,
    selectedCard: CardDO?,
    onClick: (Boolean, CardSelectionType) -> Unit
) {
    val cardBackground = when (selectedCard?.cardType) {
        CardType.VIRTUAL -> Brush.horizontalGradient(
            colors = listOf(Color(0xFF232526), Color(0xFF414345))
        )
        CardType.DEBIT -> Brush.horizontalGradient(
            colors = listOf(Color(0xFF0F2027), Color(0xFF2C5364))
        )
        null -> Brush.horizontalGradient(
            colors = listOf(Color(0xFF414345), Color(0xFF232526))
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF64748B),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
        )
        Surface(
            onClick = {
                onClick(
                    true,
                    if(isSource) CardSelectionType.SOURCE_CARD_ID else CardSelectionType.DESTINATION_CARD_ID)
            },
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
            shadowElevation = 0.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(brush = cardBackground)
                    .padding(16.dp)
            ) {
                Row(
                   modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = selectedCard?.cardType?.name ?:"",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = selectedCard?.maskedPan?:"",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                   /* Icon(
                        painter = painterResource(R.),
                        contentDescription = null,

                    )*/
                }

            }
        }
    }
}

