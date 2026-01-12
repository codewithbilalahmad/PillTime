package com.muhammad.pilltime.presentation.screens.add_medication

import com.muhammad.pilltime.domain.model.MedicineFrequency
import com.muhammad.pilltime.domain.model.MedicineType
import com.muhammad.pilltime.domain.model.MedicineSchedule
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.UUID
import kotlin.time.Clock

data class AddMedicationState(
    val medicationName : String = "",
    val dose : String = "",
    val startDate : LocalDate?=null,
    val endDate : LocalDate?=null,
    val currentMedicineId : Long = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE,
    val showMedicationDurationPickerDialog : Boolean = false,
    val showScheduleTimePickerDialog : Boolean = false,
    val selectedMedicineType : MedicineType = MedicineType.TABLET,
    val showFrequencyOptionsDropdown : Boolean = false,
    val selectedScheduleId : Long?=null,
    val selectedMedicineFrequency: MedicineFrequency = MedicineFrequency.EVERYDAY,
    val medicineSchedules : List<MedicineSchedule> = listOf(
        MedicineSchedule(time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time, medicineId = currentMedicineId)
    ).reversed(), val isCreatingMedicine : Boolean = false
){
    val isNextButtonEnabled  : Boolean = medicationName.isNotEmpty() && medicineSchedules.isNotEmpty() && startDate != null && endDate != null
}