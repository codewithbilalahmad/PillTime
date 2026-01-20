package com.muhammad.pilltime.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity

@Composable
fun DashedHorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    thickness: Float = 4f,
    dashLength: Float = 12f,
    gapLength: Float = 6f,
) {
    val density = LocalDensity.current
    val heightDp = with(density) {
        thickness.toDp()
    }
    Canvas(modifier = modifier.height(heightDp)) {
        drawLine(
            color = color,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = 0f),
            cap = StrokeCap.Round,
            strokeWidth = thickness, pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(dashLength, gapLength)
            )
        )
    }
}