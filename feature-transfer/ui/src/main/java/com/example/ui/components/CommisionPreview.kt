package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.data.domain.response.CommissionPreviewResponseDO
import com.example.ui.presentation.TransferContract

@Composable
fun CommissionPreview(
    state: TransferContract.State,
    isLoading: Boolean
) {
    val preview = state.commissionPreviewResponse
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CommissionPreviewRow(
                label = "Amount",
                value = preview?.amount?.plus(" ${state.currency}") ?: "—"
            )
            CommissionPreviewRow(
                label = "Commission (${preview?.commissionRate ?: "—"})",
                value = preview?.commissionAmount?.plus(" ${state.currency}") ?: "—",
                //value = if (isLoading) "Calculating..." else if (amount > 0) String.format("%.2f ₼", commission) else "—",
                isLoading = isLoading
            )

            HorizontalDivider(color = Color(0xFFF1F5F9))

            CommissionPreviewRow(
                label = "Total Amount",
                value = preview?.totalAmount?.plus(" ${state.currency}") ?: "—",
                isTotal = true
            )
        }
    }
}

private val mockCommissionData = CommissionPreviewResponseDO(
    amount = "150.00",
    commissionRate = "1%",
    commissionAmount = "1.50",
    totalAmount = "151.50",
    currency = "",
    isLocal = false,
    requires3DS = false
)

private val mockStateWithData = TransferContract.State(
    currency = "AZN",
    commissionPreviewResponse = mockCommissionData
)

private val mockEmptyState = TransferContract.State(
    currency = "AZN",
    commissionPreviewResponse = null
)
