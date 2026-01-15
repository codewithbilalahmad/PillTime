package com.muhammad.pilltime.presentation.screens.medication_history

import com.muhammad.pilltime.domain.model.ScheduleHistory

data class MedicationHistoryState(
    val medicationHistory : List<ScheduleHistory> = emptyList()
)
