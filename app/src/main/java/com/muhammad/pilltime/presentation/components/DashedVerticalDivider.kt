package com.muhammad.pilltime.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DashedVerticalDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    dashHeight: Dp = 6.dp,
    thickness: Dp = 1.5.dp,
    gapHeight: Dp = 4.dp,
) {
    Canvas(modifier = modifier
        .width(thickness)
        .fillMaxHeight()) {
        val dashPx = dashHeight.toPx()
        val gapPx = gapHeight.toPx()
        var startY = 0f
        while (startY < size.height) {
            drawLine(
                color = color,
                start = Offset(x = size.width / 2, y = startY),
                end = Offset(x = size.width / 2, y = startY + dashPx),
                strokeWidth = thickness.toPx(), cap = StrokeCap.Round
            )
            startY += dashPx + gapPx
        }
    }
}