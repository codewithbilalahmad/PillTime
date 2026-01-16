package com.muhammad.pilltime.presentation.screens.medication_history

import com.muhammad.pilltime.domain.model.ScheduleHistory
import com.muhammad.pilltime.utils.UiText

data class MedicationHistoryState(
    val medicationHistory : Map<UiText,List<ScheduleHistory>> = emptyMap()
)
