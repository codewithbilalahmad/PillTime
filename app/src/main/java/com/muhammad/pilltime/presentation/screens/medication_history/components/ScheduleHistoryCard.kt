package com.muhammad.pilltime.presentation.screens.medication_history.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import com.muhammad.pilltime.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammad.pilltime.domain.model.ScheduleHistory
import com.muhammad.pilltime.domain.model.ScheduleStatus
import com.muhammad.pilltime.presentation.theme.Green
import com.muhammad.pilltime.utils.formattedTime

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ScheduleHistoryCard(modifier: Modifier = Modifier, scheduleHistory: ScheduleHistory) {
    val scheduleColor by animateColorAsState(
        targetValue = when (scheduleHistory.status) {
            ScheduleStatus.DONE -> Green
            else -> MaterialTheme.colorScheme.error
        }, animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(), label = "borderColor"
    )
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.5.dp, color = scheduleColor
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(55.dp)
                            .clip(CircleShape)
                            .background(scheduleColor)
                            .dropShadow(
                                shape = CircleShape, shadow = Shadow(
                                    radius = 4.dp,
                                    spread = 4.dp,
                                    color = scheduleColor,
                                )
                            ), contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(scheduleHistory.medicineType.icon),
                            contentDescription = null, tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = scheduleHistory.medicineName,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "${scheduleHistory.dosage} ${stringResource(R.string.dose)}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.surface
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(scheduleColor)
                            )
                            Text(
                                text = stringResource(scheduleHistory.frequency.label),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.surface
                                )
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .border(
                            width = 1.5.dp,
                            color = scheduleColor,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = stringResource(scheduleHistory.status.label).uppercase(),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = scheduleColor,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                    .background(
                        MaterialTheme.colorScheme.background
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(scheduleHistory.status.icon),
                        contentDescription = null, tint = scheduleColor
                    )
                    Text(
                        text = stringResource(scheduleHistory.status.label),
                        style = MaterialTheme.typography.bodyMedium.copy(color = scheduleColor)
                    )
                }
                Text(
                    text = scheduleHistory.medicineTime.formattedTime(),
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.surface)
                )
            }
        }
    }
}