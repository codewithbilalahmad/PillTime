package com.muhammad.pilltime.presentation.screens.add_medication.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.muhammad.pilltime.R
import com.muhammad.pilltime.domain.model.Schedule
import com.muhammad.pilltime.presentation.components.AppTextField
import com.muhammad.pilltime.utils.formattedTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
fun LazyListScope.medicationScheduleSection(
    schedules: List<Schedule>,
    onAddSchedule: () -> Unit,
    onDeleteSchedule: (String) -> Unit,
    dose: String,
    onShowScheduleTimePickerDialog: (Schedule) -> Unit,
    onDoseChange: (String) -> Unit,
) {
    item("schedule_header") {
        Text(
            text = stringResource(R.string.schedule),
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
    }
    itemsIndexed(schedules, key = { _, item -> item.id }) { index, schedule ->
        val isFirstScheme = index == 0
        AppTextField(
            value = schedule.time.formattedTime(),
            enabled = false,
            hint = R.string.medication_schedule,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = if (isFirstScheme) R.drawable.ic_add else R.drawable.ic_delete,
            onTrailingIconClick = {
                if (isFirstScheme) onAddSchedule() else onDeleteSchedule(schedule.id)
            }, onClick ={
                onShowScheduleTimePickerDialog(schedule)
            },
            trailingBackground = if (isFirstScheme) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )
    }
    item("dose_text_field") {
        AppTextField(
            value = dose,
            onValueChange = onDoseChange,
            modifier = Modifier.fillMaxWidth(),
            hint = R.string.dose_optional, keyboardType = KeyboardType.Number
        )
    }
}