package com.muhammad.pilltime.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.muhammad.pilltime.R
import com.muhammad.pilltime.domain.model.Medicine
import com.muhammad.pilltime.presentation.components.WeekCalendar
import com.muhammad.pilltime.utils.relativePosition
import kotlinx.datetime.LocalDate

fun LazyListScope.medicationDataSection(
    medications: List<Medicine>,
    selectedDate: LocalDate?,
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
    if (medications.isEmpty()) {
        item("NoMedicineSection") {
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
                    )
                )
            }
        }
    } else {
        items(medications, key = { it.id }) { medicine ->
            MedicationCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .animateItem(),
                medicine = medicine,
                relativePosition = medications.relativePosition(medicine)
            )
        }
    }
}