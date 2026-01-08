package com.muhammad.pilltime.presentation.screens.add_medication

import androidx.lifecycle.ViewModel
import com.muhammad.pilltime.domain.model.MedicineFrequency
import com.muhammad.pilltime.domain.model.MedicineType
import com.muhammad.pilltime.domain.model.Schedule
import com.muhammad.pilltime.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class AddMedicationViewModel(
    private val medicationRepository: MedicationRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(AddMedicationState())
    val state = _state.asStateFlow()
    fun onAction(action: AddMedicationAction) {
        when (action) {
            is AddMedicationAction.OnMedicationNameChange -> onMedicationNameChange(action.name)
            is AddMedicationAction.OnSelectMedicineType -> onSelectMedicineType(action.type)
            is AddMedicationAction.OnSelectMedicationFrequency -> onSelectMedicationFrequency(action.frequency)
            AddMedicationAction.OnToggleFrequencyOptionsDropdown -> onToggleFrequencyOptionsDropdown()
            is AddMedicationAction.OnDoseChange -> onDoseChange(action.dose)
            AddMedicationAction.OnAddSchedule -> onAddSchedule()
            is AddMedicationAction.OnDeleteSchedule -> onDeleteSchedule(action.id)
            is AddMedicationAction.OnShowScheduleTimePickerDialog -> onShowScheduleTimePickerDialog(
                id = action.id
            )

            is AddMedicationAction.OnScheduleTimeChange -> onScheduleTimeChange(
                time = action.time
            )

            AddMedicationAction.OnDismissScheduleTimePickerDialog -> onDismissScheduleTimePickerDialog()
            is AddMedicationAction.OnSelectMedicationDuration -> onSelectMedicationDuration(
                startDate = action.startDate,
                endDate = action.endDate
            )

            AddMedicationAction.OnToggleMedicationDurationPickerDialog -> onToggleMedicationDurationPickerDialog()
        }
    }

    private fun onToggleMedicationDurationPickerDialog() {
        _state.update { it.copy(showMedicationDurationPickerDialog = !it.showMedicationDurationPickerDialog) }
    }

    private fun onSelectMedicationDuration(
        startDate: LocalDate,
        endDate: LocalDate,
    ) {
        _state.update {
            it.copy(
                startDate = startDate,
                endDate = endDate,
                showMedicationDurationPickerDialog = false
            )
        }
    }

    private fun onDismissScheduleTimePickerDialog() {
        _state.update { it.copy(showScheduleTimePickerDialog = false) }
    }

    private fun onScheduleTimeChange(time: LocalTime) {
        _state.update {
            it.copy(medicineSchedules = it.medicineSchedules.map { schedule ->
                if (schedule.id == it.selectedScheduleId) {
                    schedule.copy(time = time)
                } else schedule
            }, showScheduleTimePickerDialog = false)
        }
    }

    private fun onShowScheduleTimePickerDialog(id: String) {
        _state.update {
            it.copy(
                showScheduleTimePickerDialog = true,
                selectedScheduleId = id
            )
        }
    }

    private fun onAddSchedule() {
        val schedule = Schedule(
            time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
        )
        _state.update { it.copy(medicineSchedules = it.medicineSchedules + schedule) }
    }

    private fun onDeleteSchedule(id: String) {
        _state.update {
            it.copy(medicineSchedules = it.medicineSchedules.filter { schedule ->
                schedule.id != id
            })
        }
    }

    private fun onDoseChange(dose: String) {
        _state.update { it.copy(dose = dose) }
    }

    private fun onToggleFrequencyOptionsDropdown() {
        _state.update { it.copy(showFrequencyOptionsDropdown = !it.showFrequencyOptionsDropdown) }
    }

    private fun onSelectMedicationFrequency(frequency: MedicineFrequency) {
        _state.update {
            it.copy(
                selectedMedicineFrequency = frequency,
                showFrequencyOptionsDropdown = false
            )
        }
    }

    private fun onSelectMedicineType(type: MedicineType) {
        _state.update { it.copy(selectedMedicineType = type) }
    }

    private fun onMedicationNameChange(name: String) {
        _state.update { it.copy(medicationName = name) }
    }
}