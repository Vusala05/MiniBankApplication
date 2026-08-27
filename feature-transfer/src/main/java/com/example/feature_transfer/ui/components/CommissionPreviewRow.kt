package com.example.feature_transfer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CommissionPreviewRow(
    label: String,
    value: String,
    isTotal: Boolean = false,
    isLoading: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = if (isTotal) 14.sp else 13.sp,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Medium,
            color = if (isTotal) Color(0xFF0F172A) else Color(0xFF64748B)
        )
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(14.dp),
                strokeWidth = 2.dp,
                color = Color(0xFF2563EB)
            )
        } else {
            Text(
                text = value,
                fontSize = if (isTotal) 16.sp else 13.sp,
                fontWeight = if (isTotal) FontWeight.Bold else FontWeight.SemiBold,
                color = if (isTotal) Color(0xFF2563EB) else Color(0xFF0F172A)
            )
        }
    }
}