package com.example.feature_auth.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PinCircles(
    filledCount: Int,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    val borderColor = if (isError) Color.Red else Color.Black
    val fillColor = when {
        isError -> Color.Red
        else -> Color.Black
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(4) { index ->
            val isFilled = index < filledCount
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .then(
                        if (isFilled) {
                            Modifier.background(fillColor, CircleShape)
                        } else {
                            Modifier.border(
                                width = 2.dp,
                                color = borderColor,
                                shape = CircleShape
                            )
                        }
                    )
            )
        }
    }
}