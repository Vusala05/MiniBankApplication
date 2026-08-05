package com.example.feature_transfer.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feature_transfer.ui.presentation.TransferContract

@Composable
fun TextFieldSection(
    modifier: Modifier = Modifier,
    text : String,
    value : String,
    onValueChange : (String) -> Unit
){
    Text(
        text = "To",
        fontSize = 13.sp,
        color = Color.Gray,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        placeholder = { Text(text) }
    )

}