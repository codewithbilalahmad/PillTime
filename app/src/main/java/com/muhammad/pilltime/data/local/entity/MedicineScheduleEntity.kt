package com.muhammad.pilltime.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.muhammad.pilltime.domain.model.ScheduleStatus

@Entity
data class MedicineScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val medicineId : Long,
    val medicineTime : Int,
    val status: ScheduleStatus,
)
