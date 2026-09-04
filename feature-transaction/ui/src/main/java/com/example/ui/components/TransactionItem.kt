package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.data.util.TransactionStatus
import com.example.data.data.util.TransactionType
import com.example.data.domain.response.TransactionDO


@Composable
fun TransactionItem(
    transaction: TransactionDO,
    modifier: Modifier = Modifier
) {
    val isTopUp = transaction.type == TransactionType.TOP_UP
    val amountColor = if (isTopUp) Color(0xFF2E7D32) else Color.Red
    val amountPrefix = if (isTopUp) "+" else "-"

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = transaction.merchantName ?: transaction.type.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "$amountPrefix${transaction.amount} ${transaction.currency}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = amountColor
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = transaction.timestamp,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = "${transaction.type.name} • ${transaction.status.name}",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = when (transaction.status) {
                    TransactionStatus.COMPLETED -> Color.Gray
                    TransactionStatus.PENDING -> Color(0xFFFFA000)
                    TransactionStatus.FAILED -> Color.Red
                }
            )
        }
    }
}

