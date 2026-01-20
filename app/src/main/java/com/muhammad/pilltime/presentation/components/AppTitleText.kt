package com.muhammad.pilltime.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun AppTitleText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    highlightedColor: Color = MaterialTheme.colorScheme.error,
) {
    val lastSpaceIndex = text.lastIndexOf(" ")
    val startIndex = if (lastSpaceIndex != -1) lastSpaceIndex + 1 else 0
    val annotatedString = buildAnnotatedString {
        withStyle(
            SpanStyle(
                color = textStyle.color,
                fontSize = textStyle.fontSize,
                fontWeight = FontWeight.Bold
            )
        ){
            append(text.substring(0, startIndex))
        }
        withStyle(
            SpanStyle(
                color = highlightedColor,
                fontSize = textStyle.fontSize,
                fontWeight = FontWeight.Bold
            )
        ){
            append(text.substring(startIndex))
        }
    }
    Text(text = annotatedString, style = textStyle, modifier = modifier)
}