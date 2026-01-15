package com.muhammad.pilltime.data.local.entity

import androidx.room.*
import com.muhammad.pilltime.domain.model.MedicineFrequency
import com.muhammad.pilltime.domain.model.MedicineType
import com.muhammad.pilltime.domain.model.ScheduleStatus

@Entity
data class ScheduleHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val medicineId: Long,
    val scheduleId: Long,
    val medicineName : String,
    val dosage : Int,
    val medicineType : MedicineType,
    val frequency: MedicineFrequency,
    val status: ScheduleStatus,
    val date : Long,
    val medicineTime : Int
)
