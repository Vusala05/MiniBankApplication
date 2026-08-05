package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.domain.response.BalanceDO

@Composable
fun BalanceSection(
    balanceDO: BalanceDO?,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Balance",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )

        if (isLoading) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(24.dp),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(
                    modifier = Modifier.size(30.dp),
                    color = Color.Black,
                    strokeWidth = 3.dp
                )
            }
        } else if(balanceDO!=null) {
            Text(
                text = "${balanceDO.amount} ${balanceDO.currency}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
@Preview(showBackground = true, name = "Balance Loaded")
@Composable
fun BalanceSectionPreview() {
    Surface(modifier = Modifier.padding(16.dp)) {
        BalanceSection(
            balanceDO = BalanceDO(
                maskedPan = "4111ABCD1234",
                amount = "150.50",
                currency = "AZN"
            ),
            isLoading = false
        )
    }
}

@Preview(showBackground = true, name = "Balance Loading")
@Composable
fun BalanceSectionLoadingPreview() {
    Surface(modifier = Modifier.padding(16.dp)) {
        BalanceSection(
            balanceDO = null,
            isLoading = true
        )
    }
}
