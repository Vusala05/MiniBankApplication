package com.example.feature_transfer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultStatusScreen(
    status: String,
    transactionId: String,
    onDoneClick: () -> Unit
) {
    val isSuccess = status.equals("SUCCESS", ignoreCase = true)
    val context = LocalContext.current

    val primaryColor = if (isSuccess) Color(0xFF10B981) else Color(0xFFEF4444)
    val lightBgColor = if (isSuccess) Color(0xFFD1FAE5) else Color(0xFFFEE2E2)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF8F9FA)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
           /* Spacer(modifier = Modifier.height(32.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .background(lightBgColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isSuccess) Icons.Default.Check else Icons.Default.Close,
                        contentDescription = null,
                        tint = primaryColor,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))*/

                Text(
                    text = if (isSuccess) "Transfer Successful))" else "Transfer Failed((",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )

                Text(
                    text = if (isSuccess)
                        "Your transaction has been processed successfully."
                    else
                        "Something went wrong with your transaction. Please try again.",
                    fontSize = 14.sp,
                    color = Color(0xFF64748B),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                /*Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Status Sətri
                        ResultRow(
                            label = "Status",
                            value = if (isSuccess) "Completed" else "Failed",
                            valueColor = primaryColor
                        )

                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        // Transaction ID Sətri (Copy düyməsi ilə)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Transaction ID",
                                    fontSize = 12.sp,
                                    color = Color(0xFF64748B),
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = transactionId,
                                    fontSize = 14.sp,
                                    color = Color(0xFF0F172A),
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }

                            IconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(transactionId))
                                    Toast.makeText(context, "ID copied to clipboard", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                *//*Icon(
                                   // imageVector = Icons.Outlined.ContentCopy,
                                    contentDescription = "Copy ID",
                                    tint = Color(0xFF2563EB),
                                    modifier = Modifier.size(20.dp)
                                )*//*
                            }
                        }
                    }
                }
            }*/

            Button(
                onClick = onDoneClick,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0F172A)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Back to Home",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

/*@Composable
private fun ResultRow(
    label: String,
    value: String,
    valueColor: Color = Color(0xFF0F172A)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color(0xFF64748B),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = valueColor,
            fontWeight = FontWeight.Bold
        )
    }
}*/

