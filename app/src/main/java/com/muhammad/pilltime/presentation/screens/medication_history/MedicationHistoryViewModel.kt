package com.muhammad.pilltime.presentation.screens.medication_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.pilltime.domain.repository.ScheduleHistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MedicationHistoryViewModel(
    private val scheduleHistoryRepository: ScheduleHistoryRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(MedicationHistoryState())
    val state = combine(
        _state, scheduleHistoryRepository.getSchedulesHistory()
    ) { state, medicationHistory ->
        state.copy(medicationHistory = medicationHistory)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), MedicationHistoryState())
}