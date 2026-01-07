package com.muhammad.pilltime.presentation.screens.add_medication

import com.muhammad.pilltime.domain.model.MedicineType

sealed interface AddMedicationAction{
    data class OnMedicationNameChange(val name : String) : AddMedicationAction
    data class OnSelectMedicineType(val type : MedicineType) : AddMedicationAction
}