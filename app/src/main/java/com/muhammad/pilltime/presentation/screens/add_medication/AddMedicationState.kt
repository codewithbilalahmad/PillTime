package com.muhammad.pilltime.presentation.screens.add_medication

import com.muhammad.pilltime.domain.model.MedicineType

data class AddMedicationState(
    val medicationName : String = "",
    val selectedMedicineType : MedicineType = MedicineType.TABLET,
)