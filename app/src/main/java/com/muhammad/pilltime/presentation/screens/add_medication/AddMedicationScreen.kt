package com.muhammad.pilltime.presentation.screens.add_medication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.pilltime.R
import com.muhammad.pilltime.presentation.components.AppTextField
import com.muhammad.pilltime.presentation.components.DashedHorizontalDivider
import com.muhammad.pilltime.presentation.components.PrimaryButton
import com.muhammad.pilltime.presentation.navigation.Destinations
import com.muhammad.pilltime.presentation.screens.add_medication.components.FrequencyTextField
import com.muhammad.pilltime.presentation.screens.add_medication.components.MedicationDurationPickerDialog
import com.muhammad.pilltime.presentation.screens.add_medication.components.MedicationTypeSection
import com.muhammad.pilltime.presentation.screens.add_medication.components.medicationScheduleSection
import com.muhammad.pilltime.utils.ObserveAsEvents
import com.muhammad.pilltime.utils.formattedFullDuration
import kotlinx.datetime.LocalTime
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddMedicationScreen(
    navHostController: NavHostController,
    viewModel: AddMedicationViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val layoutDirection = LocalLayoutDirection.current
    val timePickerState = rememberTimePickerState(is24Hour = false)
    ObserveAsEvents(flow = viewModel.events, onEvent = { event ->
        when (event) {
            is AddMedicationEvent.OnCreateMedicationSuccess -> {
                navHostController.navigate(Destinations.AddMedicationSuccessScreen(event.medicine)) {
                    popUpTo(Destinations.AddMedicationScreen) {
                        inclusive = true
                    }
                }
            }
        }
    })
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Column(modifier = Modifier.fillMaxWidth()){
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(R.string.new_medication))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.navigateUp()
                    }, shapes = IconButtonDefaults.shapes()) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
            DashedHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 8.dp,
                start = paddingValues.calculateStartPadding(layoutDirection) + 12.dp,
                end = paddingValues.calculateEndPadding(layoutDirection) + 12.dp,
                bottom = paddingValues.calculateBottomPadding() + 32.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item("medication_name") {
                AppTextField(value = state.medicationName, onValueChange = { newValue ->
                    viewModel.onAction(AddMedicationAction.OnMedicationNameChange(newValue))
                }, modifier = Modifier.fillMaxWidth(), hint = R.string.medication_name)
            }
            item("medication_duration") {
                val formattedDuration =
                    state.startDate?.formattedFullDuration(state.endDate!!) ?: ""
                AppTextField(
                    value = formattedDuration,
                    onValueChange = { newValue ->
                        viewModel.onAction(AddMedicationAction.OnMedicationNameChange(newValue))
                    }, onClick = {
                        viewModel.onAction(AddMedicationAction.OnToggleMedicationDurationPickerDialog)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    enabled = false, onTrailingIconClick = {
                        viewModel.onAction(AddMedicationAction.OnToggleMedicationDurationPickerDialog)
                    },
                    hint = R.string.select_duration,
                    trailingIcon = R.drawable.ic_date
                )
            }
            item("FrequencyTextField") {
                FrequencyTextField(
                    modifier = Modifier.fillMaxWidth(),
                    showFrequencyOptionsDropDown = state.showFrequencyOptionsDropdown,
                    frequency = state.selectedMedicineFrequency,
                    onFrequencyChange = { frequency ->
                        viewModel.onAction(AddMedicationAction.OnSelectMedicationFrequency(frequency))
                    }, onToggleFrequencyOptionsDropDown = {
                        viewModel.onAction(AddMedicationAction.OnToggleFrequencyOptionsDropdown)
                    })
            }
            medicationScheduleSection(
                dose = state.dose,
                onDoseChange = { newValue ->
                    viewModel.onAction(AddMedicationAction.OnDoseChange(newValue))
                },
                onAddSchedule = {
                    viewModel.onAction(AddMedicationAction.OnAddSchedule)
                },
                onDeleteSchedule = { id ->
                    viewModel.onAction(AddMedicationAction.OnDeleteSchedule(id))
                },
                schedules = state.medicineSchedules,
                onShowScheduleTimePickerDialog = { schedule ->
                    timePickerState.hour = schedule.time.hour
                    timePickerState.minute = schedule.time.minute
                    viewModel.onAction(AddMedicationAction.OnShowScheduleTimePickerDialog(schedule.id))
                }
            )

            item("MedicationTypeSection") {
                MedicationTypeSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    onSelectMedicationType = { type ->
                        viewModel.onAction(AddMedicationAction.OnSelectMedicineType(type))
                    },
                    selectedMedicineType = state.selectedMedicineType
                )
            }
            item("AddMedicationButton") {
                PrimaryButton(
                    text = stringResource(R.string.next),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp), isLoading = state.isCreatingMedicine,
                    onClick = {
                        viewModel.onAction(AddMedicationAction.OnCreateMedication)
                    }, enabled = state.isNextButtonEnabled,
                    contentPadding = PaddingValues(vertical = 16.dp)
                )
            }
        }
    }
    if (state.showScheduleTimePickerDialog) {
        TimePickerDialog(
            onDismissRequest = {
                viewModel.onAction(AddMedicationAction.OnDismissScheduleTimePickerDialog)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedTime = LocalTime(
                            hour = timePickerState.hour,
                            minute = timePickerState.minute,
                        )
                        viewModel.onAction(AddMedicationAction.OnScheduleTimeChange(selectedTime))
                    },
                    shapes = ButtonDefaults.shapes()
                ) {
                    Text(text = stringResource(R.string.done))
                }
            }, dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.onAction(AddMedicationAction.OnDismissScheduleTimePickerDialog)
                    },
                    shapes = ButtonDefaults.shapes()
                ) {
                    Text(text = stringResource(R.string.dismiss))
                }
            }, title = {
                Text(text = stringResource(R.string.select_time))
            }) {
            TimePicker(state = timePickerState, layoutType = TimePickerLayoutType.Vertical)
        }
    }
    MedicationDurationPickerDialog(
        showMedicationDurationPickerDialog = state.showMedicationDurationPickerDialog,
        startDate = state.startDate,
        endDate = state.endDate,
        onSelectMedicationDuration = { startDate, endDate ->
            viewModel.onAction(AddMedicationAction.OnSelectMedicationDuration(startDate, endDate))
        },
        onDismissRequest = {
            viewModel.onAction(AddMedicationAction.OnToggleMedicationDurationPickerDialog)
        })
}