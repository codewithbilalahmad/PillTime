package com.muhammad.pilltime.presentation.screens.medication_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.pilltime.R
import com.muhammad.pilltime.domain.model.ScheduleHistory
import com.muhammad.pilltime.domain.repository.ScheduleHistoryRepository
import com.muhammad.pilltime.utils.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class MedicationHistoryViewModel(
    private val scheduleHistoryRepository: ScheduleHistoryRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(MedicationHistoryState())
    val state = combine(
        _state, scheduleHistoryRepository.getSchedulesHistory()
    ) { state, medicationHistory ->
        state.copy(medicationHistory = medicationHistory.groupByRelativeDate())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), MedicationHistoryState())

    private fun List<ScheduleHistory>.groupByRelativeDate(): Map<UiText, List<ScheduleHistory>> {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val yesterday = today.minus(DatePeriod(days = 1))
        return this.sortedWith(
            compareByDescending<ScheduleHistory>{ it.date }.thenByDescending { it.medicineTime }
        ).groupBy { history ->
            when(history.date){
                today -> UiText.StringResource(R.string.today)
                yesterday -> UiText.StringResource(R.string.yesterday)
                else -> UiText.Dynamic(history.date.toString())
            }
        }
    }
}