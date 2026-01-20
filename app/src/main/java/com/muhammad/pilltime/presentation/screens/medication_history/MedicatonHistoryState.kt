package com.muhammad.pilltime.presentation.screens.medication_history

import com.muhammad.pilltime.domain.model.ScheduleDaySection
import com.muhammad.pilltime.domain.model.ScheduleHistory
import com.muhammad.pilltime.utils.UiText
import kotlinx.datetime.LocalDate

data class MedicationHistoryState(
    val medicationHistory: Map<UiText, List<ScheduleHistory>> = emptyMap(),
    val selectedFilterDate : LocalDate?=null
) {
    val scheduleDaySections = medicationHistory.toList()
        .map { (dateHeader, schedulesHistory) -> ScheduleDaySection(dateHeader, schedulesHistory) }
}
