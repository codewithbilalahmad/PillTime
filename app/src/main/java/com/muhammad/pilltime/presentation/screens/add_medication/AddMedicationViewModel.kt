package com.muhammad.pilltime.presentation.screens.add_medication

import androidx.lifecycle.ViewModel
import com.muhammad.pilltime.domain.model.MedicineType
import com.muhammad.pilltime.domain.repository.MedicationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddMedicationViewModel(
    private val medicationRepository: MedicationRepository
): ViewModel(){
    private val _state = MutableStateFlow(AddMedicationState())
    val state = _state.asStateFlow()
    fun onAction(action: AddMedicationAction){
        when(action){
            is AddMedicationAction.OnMedicationNameChange -> onMedicationNameChange(action.name)
            is AddMedicationAction.OnSelectMedicineType -> onSelectMedicineType(action.type)
        }
    }

    private fun onSelectMedicineType(type: MedicineType) {
        _state.update { it.copy(selectedMedicineType = type) }
    }

    private fun onMedicationNameChange(name: String) {
        _state.update { it.copy(medicationName = name) }
    }
}