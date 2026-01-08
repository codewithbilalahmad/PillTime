package com.muhammad.pilltime.presentation.screens.add_medication

import com.muhammad.pilltime.domain.model.MedicineFrequency
import com.muhammad.pilltime.domain.model.MedicineType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

sealed interface AddMedicationAction {
    data class OnMedicationNameChange(val name: String) : AddMedicationAction
    data class OnDoseChange(val dose: String) : AddMedicationAction
    data class OnDeleteSchedule(val id: String) : AddMedicationAction
    data object OnAddSchedule : AddMedicationAction
    data object OnToggleMedicationDurationPickerDialog : AddMedicationAction
    data class OnSelectMedicationDuration(val startDate : LocalDate, val endDate : LocalDate) : AddMedicationAction
    data class OnScheduleTimeChange(val time: LocalTime) : AddMedicationAction
    data class OnShowScheduleTimePickerDialog(val id : String) : AddMedicationAction
    data object OnDismissScheduleTimePickerDialog : AddMedicationAction
    data class OnSelectMedicineType(val type: MedicineType) : AddMedicationAction
    data object OnToggleFrequencyOptionsDropdown : AddMedicationAction
    data class OnSelectMedicationFrequency(val frequency: MedicineFrequency) : AddMedicationAction
}