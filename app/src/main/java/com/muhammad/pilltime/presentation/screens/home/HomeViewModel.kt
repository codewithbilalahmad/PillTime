package com.muhammad.pilltime.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.pilltime.domain.model.ScheduleStatus
import com.muhammad.pilltime.domain.repository.MedicationRepository
import com.muhammad.pilltime.domain.repository.MedicationScheduleRespository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class HomeViewModel(
    private val medicationRepository: MedicationRepository,
    private val medicationScheduleRepository: MedicationScheduleRespository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = combine(
        _state, medicationRepository.getAllMedicines()
    ) { state, medications ->
        val filteredMedication = medications.filter { medicine ->
            state.selectedFilterDate >= medicine.startDate && state.selectedFilterDate <= medicine.endDate
        }
        state.copy(medications = filteredMedication)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeState())
    private val _events = Channel<HomeEvent>()
    val events = _events.receiveAsFlow()
    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnFilterDataSelected -> onFilterDataSelected(action.date)
            HomeAction.OnNotificationPermissionPermanentlyDenied -> onNotificationPermissionPermanentlyDenied()
            HomeAction.OnToggleAllowNotificationAccessDialog -> onToggleAllowNotificationAccessDialog()
            is HomeAction.OnUpdateMedicineScheduleStatus -> onUpdateMedicineScheduleStatus(
                scheduleId = action.scheduleId,
                medicineId = action.medicineId,
                status = action.status
            )

            is HomeAction.OnToggleMedicineSchedules -> onToggleMedicineSchedules(action.medicineId)
        }
    }

    private fun onToggleMedicineSchedules(medicineId: Long) {
        _state.update {
            it.copy(
                medications = it.medications.map { medicine ->
                    if(medicine.id == medicineId){
                        medicine.copy(showMedicineSchedule = !medicine.showMedicineSchedule)
                    } else medicine
                }
            )
        }
    }

    private fun onUpdateMedicineScheduleStatus(
        scheduleId: Long,
        status: ScheduleStatus,
        medicineId: Long,
    ) {
        viewModelScope.launch {
            medicationScheduleRepository.updateMedicineScheduleStatus(
                scheduleId = scheduleId,
                status = status
            )
        }
        _events.trySend(HomeEvent.ScrollToMedicine(medicineId = medicineId))
    }

    private fun onNotificationPermissionPermanentlyDenied() {
        _state.update {
            it.copy(
                isNotificationPermanentlyDenied = true,
                showAllowNotificationAccessDialog = true
            )
        }
    }

    private fun onToggleAllowNotificationAccessDialog() {
        _state.update { it.copy(showAllowNotificationAccessDialog = !it.showAllowNotificationAccessDialog) }
    }

    private fun onFilterDataSelected(date: LocalDate) {
        _state.update { it.copy(selectedFilterDate = date) }
    }
}