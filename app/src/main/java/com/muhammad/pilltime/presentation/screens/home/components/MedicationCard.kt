package com.muhammad.pilltime.presentation.screens.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.muhammad.pilltime.R
import com.muhammad.pilltime.domain.model.Medicine
import com.muhammad.pilltime.domain.model.MedicineType
import com.muhammad.pilltime.domain.model.RelativePosition
import com.muhammad.pilltime.domain.model.ScheduleStatus
import com.muhammad.pilltime.presentation.components.AppCheckBox
import com.muhammad.pilltime.presentation.components.DashedVerticalDivider
import com.muhammad.pilltime.presentation.theme.Green
import com.muhammad.pilltime.utils.formattedFullDuration
import com.muhammad.pilltime.utils.formattedTime
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MedicationCard(
    modifier: Modifier = Modifier,
    medicine: Medicine, onToggleMedicineSchedules: (Long) -> Unit,
    onDeleteMedicine: (Long) -> Unit,
    relativePosition: RelativePosition,
) {
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()
    val infiniteTransition = rememberInfiniteTransition()
    var cardSize by remember { mutableStateOf(Size.Zero) }
    val offsetX = remember { Animatable(0f) }
    Row(modifier.height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.TopCenter) {
            if (relativePosition != RelativePosition.SINGLE_ENTRY) {
                DashedVerticalDivider(color = medicine.medicineType.color)
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(medicine.medicineType.color),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(medicine.medicineType.icon),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp),
                    tint = Color.White
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.8f, targetValue = 1.2f, animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ), label = "scale"
            )
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f, targetValue = 32f, animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ), label = "rotation"
            )
            Box(
                modifier = Modifier
                    .size(
                        width = with(density) { cardSize.width.toDp() },
                        height = with(density) { cardSize.height.toDp() })
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        medicine.medicineType.color
                    )
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                    contentDescription = null, tint = MaterialTheme.colorScheme.onError,
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.CenterStart)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            rotationZ = rotation
                        }
                        .size(26.dp)
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(onDragEnd = {
                            if (offsetX.value >= cardSize.width * 0.40f) {
                                onDeleteMedicine(medicine.id)
                            } else {
                                scope.launch {
                                    offsetX.animateTo(0f)
                                }
                            }
                        }, onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            scope.launch {
                                val newOffset =     (offsetX.value + dragAmount).coerceIn(0f, cardSize.width * 0.7f)
                                offsetX.snapTo(newOffset)
                            }
                        })
                    }
                    .graphicsLayer {
                        translationX = 6.dp.toPx() + offsetX.value
                    }
                    .onSizeChanged { size ->
                        cardSize = with(density) {
                            size.toSize()
                        }
                    },
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(width = 1.5.dp, color = medicine.medicineType.color),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_frequency),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.surface
                        )
                        Text(
                            text = stringResource(medicine.frequency.label),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.surface
                            )
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = medicine.name,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1,
                            overflow = TextOverflow.Clip
                        )
                        AnimatedVisibility(
                            visible = medicine.selectedDateProgress == 1f,
                            enter = scaleIn(animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()),
                            exit = scaleOut(animationSpec = MaterialTheme.motionScheme.slowEffectsSpec())
                        ) {
                            AppCheckBox(checked = true, selectedCheckColor = Green, size = 22.dp) {

                            }
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            val dosageUnit = when (medicine.medicineType) {
                                MedicineType.TABLET,
                                MedicineType.CAPSULE,
                                    -> R.string.dose

                                MedicineType.SYRUP -> R.string.spoon

                                MedicineType.DROPS -> R.string.drops

                                MedicineType.Spray -> R.string.spray

                                MedicineType.GEL -> R.string.application
                            }
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_goal),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.surface
                            )
                            Text(
                                text = "${medicine.dosage} ${stringResource(dosageUnit)}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.surface,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(width = 50.dp, height = 6.dp)
                                    .clip(CircleShape)
                                    .background(
                                        MaterialTheme.colorScheme.surfaceVariant
                                    )
                            ) {
                                val progressColor by animateColorAsState(
                                    targetValue = when (medicine.selectedDateProgress) {
                                        in 0.1f..0.3f -> MaterialTheme.colorScheme.error
                                        in 0.3f..0.6f -> MaterialTheme.colorScheme.primary
                                        else -> Green
                                    },
                                    animationSpec = MaterialTheme.motionScheme.slowEffectsSpec(),
                                    label = "progressColor"
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(medicine.selectedDateProgress)
                                        .fillMaxHeight()
                                        .clip(CircleShape)
                                        .background(progressColor)
                                )
                            }
                            val percentageColor by animateColorAsState(
                                targetValue = when (medicine.selectedDateProgress) {
                                    in 0f..0.3f -> MaterialTheme.colorScheme.error
                                    in 0.3f..0.6f -> MaterialTheme.colorScheme.primary
                                    else -> Green
                                },
                                animationSpec = MaterialTheme.motionScheme.slowEffectsSpec(),
                                label = "progressColor"
                            )
                            Text(
                                text = "${(medicine.selectedDateProgress * 100).toInt()}%",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = percentageColor
                                )
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_date),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.surface
                        )
                        Text(
                            text = medicine.startDate.formattedFullDuration(medicine.endDate),
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.surface)
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.schedules),
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                        )
                        IconButton(
                            onClick = {
                                onToggleMedicineSchedules(medicine.id)
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.size(IconButtonDefaults.extraSmallContainerSize())
                        ) {
                            val icon =
                                if (medicine.showMedicineSchedule) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                            Icon(
                                imageVector = ImageVector.vectorResource(icon),
                                contentDescription = null,
                                modifier = Modifier.size(IconButtonDefaults.extraSmallIconSize)
                            )
                        }
                    }
                    AnimatedVisibility(
                        visible = medicine.showMedicineSchedule,
                        enter = slideInVertically(animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()) { -it } + expandVertically(
                            animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()
                        ) { -it },
                        exit = slideOutVertically(animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()) { -it } + shrinkVertically(
                            animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()
                        ) { -it }) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Spacer(Modifier.height(8.dp))
                            medicine.schedules.forEach { schedule ->
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    val contentColor = when (schedule.status) {
                                        ScheduleStatus.PENDING -> MaterialTheme.colorScheme.primary
                                        ScheduleStatus.DONE -> Green
                                        ScheduleStatus.MISSED -> MaterialTheme.colorScheme.error
                                    }
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ic_time),
                                        contentDescription = null,
                                        tint = contentColor,
                                        modifier = Modifier
                                            .size(22.dp)
                                            .align(Alignment.CenterStart)
                                    )
                                    Text(
                                        text = schedule.time.formattedTime(),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = contentColor
                                        )
                                    )
                                    Icon(
                                        imageVector = ImageVector.vectorResource(schedule.status.icon),
                                        contentDescription = null,
                                        tint = contentColor,
                                        modifier = Modifier
                                            .size(22.dp)
                                            .align(Alignment.CenterEnd)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}