package com.muhammad.pilltime.presentation.screens.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.muhammad.pilltime.R
import com.muhammad.pilltime.domain.model.Medicine
import com.muhammad.pilltime.domain.model.MedicineType
import com.muhammad.pilltime.domain.model.RelativePosition
import com.muhammad.pilltime.presentation.components.DashedVerticalDivider
import com.muhammad.pilltime.utils.formattedFullDuration
import com.muhammad.pilltime.utils.formattedTime

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MedicationCard(
    modifier: Modifier = Modifier,
    medicine: Medicine,
    relativePosition: RelativePosition,
) {
    var showSchedules by remember { mutableStateOf(false) }
    Row(modifier.height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.TopCenter) {
            if (relativePosition != RelativePosition.SINGLE_ENTRY) {
                DashedVerticalDivider()
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(width = 1.5.dp, color = MaterialTheme.colorScheme.surfaceVariant),
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
                        modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.surface
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
                Text(
                    text = medicine.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Spacer(Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ){
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
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.surface, fontWeight = FontWeight.Bold)
                    )
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
                            showSchedules = !showSchedules
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.size(IconButtonDefaults.extraSmallContainerSize())
                    ) {
                        val icon =
                            if (showSchedules) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                        Icon(
                            imageVector = ImageVector.vectorResource(icon),
                            contentDescription = null,
                            modifier = Modifier.size(IconButtonDefaults.extraSmallIconSize)
                        )
                    }
                }
                AnimatedVisibility(
                    visible = showSchedules,
                    enter = slideInVertically(animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()) { -it } + expandVertically(
                        animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()
                    ) { -it },
                    exit = slideOutVertically(animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()) { -it } + shrinkVertically(
                        animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()
                    ) { -it }) {
                    Spacer(Modifier.height(8.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        medicine.schedules.forEach { schedule ->
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_time),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.surface,
                                    modifier = Modifier
                                        .size(22.dp)
                                        .align(Alignment.CenterStart)
                                )
                                Text(
                                    text = schedule.time.formattedTime(),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.surface
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}