package com.muhammad.pilltime.presentation.screens.medication_history

import kotlinx.datetime.LocalDate

sealed interface MedicationHistoryAction{
    data class OnFilterDateChange(val date : LocalDate) : MedicationHistoryAction
}