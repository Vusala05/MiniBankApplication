package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.data.util.CardType
import com.example.data.domain.response.CardDO
import com.example.feature_card.data.util.CardStatus

@Composable
fun BankCardItem(
    card: CardDO?,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    val cardBackground = when (card?.cardType) {
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

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(cardBackground)
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(36.dp),
                    color = Color.White,
                    strokeWidth = 3.dp
                )
            } else if (card != null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = card.cardType.name,
                            color = Color.LightGray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )

                        Box(
                            modifier = Modifier
                                .background(
                                    color = when (card.status) {
                                        CardStatus.ACTIVE -> Color(0xFF4CAF50)
                                        CardStatus.EXPIRED -> Color.Gray
                                        else -> Color.Red
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = card.status.name,
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text(
                        text = card.maskedPan.chunked(4).joinToString(" "),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 2.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "CARDHOLDER",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 9.sp
                            )
                            Text(
                                text = card.cardholderName.uppercase(),
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "EXPIRES",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 9.sp
                            )
                            Text(
                                text = card.expirationDate,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun LoadingCardPreview() {
    Surface(modifier = Modifier.padding(16.dp)) {
        BankCardItem(
            card = null,
            isLoading = true
        )
    }
}

@Preview(showBackground = true, name = "Virtual Card")
@Composable
fun VirtualCardPreview() {
    Surface(modifier = Modifier.padding(16.dp)) {
        BankCardItem(
            card = CardDO(
                id = "1",
                maskedPan = "4111ABCD1234",
                cardholderName = "Vusala Isgandarova",
                cardType = CardType.VIRTUAL,
                status = CardStatus.ACTIVE,
                expirationDate = "05/30",
                currency = "AZN"
            ),
            isLoading = false
        )
    }
}