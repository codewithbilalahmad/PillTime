package com.muhammad.pilltime.presentation.screens.add_medication.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muhammad.pilltime.R
import com.muhammad.pilltime.utils.formattedDuration
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaYearMonth
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MedicationDurationPickerDialog(
    showMedicationDurationPickerDialog: Boolean,
    startDate: LocalDate?, onSelectMedicationDuration: (LocalDate, LocalDate) -> Unit,
    endDate: LocalDate?,
    onDismissRequest: () -> Unit,
) {
    if (showMedicationDurationPickerDialog) {
        val timeZone = TimeZone.currentSystemDefault()
        val today = Clock.System.now().toLocalDateTime(timeZone).date
        val initialMonth = remember(startDate) {
            val date = startDate ?: today
            YearMonth(date.year, date.month)
        }
        val dateRangePickerState = rememberDateRangePickerState(
            initialSelectedStartDate = startDate?.toJavaLocalDate(),
            initialSelectedEndDate = endDate?.toJavaLocalDate(),
            initialDisplayedMonth = initialMonth.toJavaYearMonth()
        )
        val selectedStart = dateRangePickerState.selectedStartDateMillis?.let { millis ->
            Instant.fromEpochMilliseconds(millis).toLocalDateTime(timeZone).date
        }
        val selectedEnd = dateRangePickerState.selectedEndDateMillis?.let { millis ->
            Instant.fromEpochMilliseconds(millis).toLocalDateTime(timeZone).date
        }
        val isDateSelectable =
            dateRangePickerState.selectedStartDateMillis != null && dateRangePickerState.selectedEndDateMillis != null
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            dismissButton = {
                TextButton(onClick = onDismissRequest, shapes = ButtonDefaults.shapes()) {
                    Text(stringResource(R.string.dismiss))
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onSelectMedicationDuration(selectedStart!!, selectedEnd!!)
                }, enabled = isDateSelectable, shapes = ButtonDefaults.shapes()) {
                    Text(stringResource(R.string.done))
                }
            }) {
            DateRangePicker(state = dateRangePickerState, title = {
                Text(
                    text = stringResource(R.string.select_duration),
                    modifier = Modifier.padding(
                        start = 24.dp,
                        end = 12.dp,
                        top = 16.dp,
                    ),
                )
            }, headline = {
                if (isDateSelectable) {
                    val formattedDuration = selectedStart?.formattedDuration(selectedEnd!!) ?: ""
                    Text(
                        text = formattedDuration, modifier = Modifier
                            .padding(
                                start = 24.dp,
                                end = 12.dp,
                                bottom = 12.dp,
                            )
                    )
                }
            })
        }
    }
}