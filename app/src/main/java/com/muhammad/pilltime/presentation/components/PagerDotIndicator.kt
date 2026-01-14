package com.muhammad.pilltime.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PagerDotIndicator(modifier: Modifier = Modifier, pagerState: PagerState) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)
    ) {
        items(pagerState.pageCount) { page ->
            val isSelected = page == pagerState.currentPage
            val background by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                animationSpec = MaterialTheme.motionScheme.slowEffectsSpec(), label = "background"
            )
            val borderColor by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                animationSpec = MaterialTheme.motionScheme.slowEffectsSpec(),
                label = "background"
            )
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .border(width = 1.5.dp, color = borderColor, shape = CircleShape)
                    .background(background)
            )
        }
    }
}