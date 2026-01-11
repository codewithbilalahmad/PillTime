package com.muhammad.pilltime.presentation.screens.add_medication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.pilltime.domain.model.Medicine
import com.muhammad.pilltime.domain.model.MedicineFrequency
import com.muhammad.pilltime.domain.model.MedicineSchedule
import com.muhammad.pilltime.domain.model.MedicineType
import com.muhammad.pilltime.domain.model.ScheduleStatus
import com.muhammad.pilltime.domain.repository.MedicationRepository
import com.muhammad.pilltime.domain.repository.MedicationScheduleRespository
import com.muhammad.pilltime.domain.repository.NotificationScheduler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

class AddMedicationViewModel(
    private val medicationRepository: MedicationRepository,
    private val medicationScheduleRepository: MedicationScheduleRespository,
    private val notificationScheduler: NotificationScheduler,
) : ViewModel() {
    private val _state = MutableStateFlow(AddMedicationState())
    val state = _state.asStateFlow()
    private val _events = Channel<AddMedicationEvent>()
    val events = _events.receiveAsFlow()
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
            AddMedicationAction.OnCreateMedication -> onCreateMedication()
        }
    }

    private fun onUpdateMedicineScheduleStatus(
        scheduleId: Long,
        status: ScheduleStatus
    ) {

    }

    private fun onCreateMedication() {
        viewModelScope.launch {
            val medication = Medicine(
                id = state.value.currentMedicineId,
                name = state.value.medicationName,
                dosage = state.value.dose.toIntOrNull() ?: 1,
                frequency = state.value.selectedMedicineFrequency,
                startDate = state.value.startDate ?: return@launch,
                endDate = state.value.endDate ?: return@launch,
                createdAt = Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds()),
                medicineType = state.value.selectedMedicineType,
                schedules = state.value.medicineSchedules
            )
            medicationRepository.insertMedicine(medication)
            medicationScheduleRepository.insertMedicineSchedules(schedules = medication.schedules)
            notificationScheduler.scheduleMedicine(medication)
            _events.trySend(AddMedicationEvent.OnCreateMedicationSuccess(medication))
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

    private fun onShowScheduleTimePickerDialog(id: Long) {
        _state.update {
            it.copy(
                showScheduleTimePickerDialog = true,
                selectedScheduleId = id
            )
        }
    }

    private fun onAddSchedule() {
        val schedule = MedicineSchedule(
            time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time,
            medicineId = state.value.currentMedicineId
        )
        _state.update { it.copy(medicineSchedules = it.medicineSchedules + schedule) }
    }

    private fun onDeleteSchedule(id: Long) {
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