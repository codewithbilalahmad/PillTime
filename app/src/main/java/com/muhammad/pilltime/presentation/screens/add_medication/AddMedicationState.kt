package com.muhammad.pilltime.presentation.screens.add_medication

import com.muhammad.pilltime.domain.model.MedicineFrequency
import com.muhammad.pilltime.domain.model.MedicineType
import com.muhammad.pilltime.domain.model.Schedule
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class AddMedicationState(
    val medicationName : String = "",
    val dose : String = "",
    val medicineSchedules : List<Schedule> = listOf(
        Schedule(time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time)
    ).reversed(),
    val startDate : LocalDate?=null,
    val endDate : LocalDate?=null,
    val showMedicationDurationPickerDialog : Boolean = false,
    val showScheduleTimePickerDialog : Boolean = false,
    val selectedMedicineType : MedicineType = MedicineType.TABLET,
    val showFrequencyOptionsDropdown : Boolean = false,
    val selectedScheduleId : String?=null,
    val selectedMedicineFrequency: MedicineFrequency = MedicineFrequency.EVERYDAY,
){
    val isNextButtonEnabled  : Boolean = medicationName.isNotEmpty() && medicineSchedules.isNotEmpty() && startDate != null && endDate != null
}