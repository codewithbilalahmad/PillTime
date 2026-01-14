package com.muhammad.pilltime.presentation.navigation

import com.muhammad.pilltime.domain.model.Medicine
import kotlinx.serialization.Serializable

sealed interface Destinations{
    @Serializable
    data object BoardingScreen : Destinations
    @Serializable
    data object UsernameScreen : Destinations
    @Serializable
    data object HomeScreen : Destinations
    @Serializable
    data object AddMedicationScreen : Destinations
    @Serializable
    data class AddMedicationSuccessScreen(val medicine: Medicine) : Destinations
    @Serializable
    data object MedicationHistoryScreen : Destinations
}