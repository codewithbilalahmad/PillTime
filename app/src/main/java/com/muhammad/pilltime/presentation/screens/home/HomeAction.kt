package com.muhammad.pilltime.presentation.screens.home

import com.muhammad.pilltime.presentation.screens.add_medication.AddMedicationAction
import kotlinx.datetime.LocalDate


sealed interface HomeAction{
    data class OnFilterDataSelected(val date : LocalDate) : HomeAction
    data object OnToggleAllowNotificationAccessDialog : HomeAction
    data object OnNotificationPermissionPermanentlyDenied : HomeAction
}