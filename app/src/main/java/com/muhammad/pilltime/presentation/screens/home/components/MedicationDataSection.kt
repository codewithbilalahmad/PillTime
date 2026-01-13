package com.muhammad.pilltime.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.muhammad.pilltime.R
import com.muhammad.pilltime.domain.model.Medicine
import com.muhammad.pilltime.presentation.components.WeekCalendar
import com.muhammad.pilltime.utils.loadingEffect
import com.muhammad.pilltime.utils.relativePosition
import kotlinx.datetime.LocalDate

fun LazyListScope.medicationDataSection(
    medications: List<Medicine>, onDeleteMedicine: (Long) -> Unit,
    isMedicinesLoading: Boolean,
    selectedDate: LocalDate?, onToggleMedicineSchedules: (Long) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
) {
    item("WeekCalendar") {
        WeekCalendar(
            selectedDate = selectedDate,
            onDateSelected = onDateSelected,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .animateItem()
        )
    }
    when {
        isMedicinesLoading -> {
            items(5, key = { it }) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .loadingEffect()
                    )
                    Box(modifier = Modifier
                        .weight(1f).height(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .loadingEffect())
                }
            }
        }

        !isMedicinesLoading && medications.isEmpty() -> {
            item("NoMedicinesSection") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.no_medicine),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.no_medicine_found),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.no_medicine_found_description),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.surface
                        ), modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }

        else -> {
            items(medications, key = { it.id }, contentType = {
                "medicine_${it.id}"
            }) { medicine ->
                MedicationCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .animateItem(),
                    medicine = medicine,
                    onDeleteMedicine = onDeleteMedicine,
                    onToggleMedicineSchedules = onToggleMedicineSchedules,
                    relativePosition = medications.relativePosition(medicine)
                )
            }
        }
    }
}