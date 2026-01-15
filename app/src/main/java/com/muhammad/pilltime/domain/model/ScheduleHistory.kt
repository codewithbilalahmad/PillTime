package com.muhammad.pilltime.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@Immutable
data class ScheduleHistory(
    val id: Long,
    val medicineId: Long,
    val scheduleId: Long,
    val medicineName : String,
    val dosage : Int,
    val medicineType : MedicineType,
    val frequency: MedicineFrequency,
    val status: ScheduleStatus,
    val date : LocalDate,
    val medicineTime : LocalTime
)
