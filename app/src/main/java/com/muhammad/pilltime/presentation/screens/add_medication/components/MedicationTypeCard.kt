package com.muhammad.pilltime.presentation.screens.add_medication.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammad.pilltime.domain.model.MedicineType

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MedicationTypeCard(
    modifier: Modifier = Modifier,
    type: MedicineType, isSelected: Boolean,
    onClick: (MedicineType) -> Unit,
) {
    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
    val containerColor by animateColorAsState(
        targetValue = if (isSelected) type.color else Color.Transparent,
        animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(), label = "containerColor"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) type.color else MaterialTheme.colorScheme.surface,
        animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(), label = "borderColor"
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(), label = "contentColor"
    )
    val shape by animateDpAsState(
        targetValue = if (isSelected) 24.dp else 16.dp,
        animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
        label = "shape"
    )
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(shape),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ), onClick = {
            onClick(type)
        },
        border = BorderStroke(width = 1.5.dp, color = borderColor)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(type.icon),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = stringResource(type.label),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = fontWeight)
            )
        }
    }
}