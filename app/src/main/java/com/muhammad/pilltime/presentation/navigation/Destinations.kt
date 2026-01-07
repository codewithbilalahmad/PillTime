package com.muhammad.pilltime.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Destinations{
    @Serializable
    data object HomeScreen : Destinations
    @Serializable
    data object AddMedicationScreen : Destinations
    @Serializable
    data object MedicationHistoryScreen : Destinations
}