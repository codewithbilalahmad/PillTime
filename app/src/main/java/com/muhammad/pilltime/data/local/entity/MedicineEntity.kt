package com.muhammad.pilltime.data.local.entity

import androidx.room.*
import com.muhammad.pilltime.domain.model.MedicineType
import kotlin.time.Instant

@Entity
data class MedicineEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val name : String,
    val dosage : Int,
    val frequency : String,
    val startDate : Long?,
    val endDate : Long,
    val medicineTaken : Boolean,
    val medicineTime : Long,
    val createdAt : Long,
    val medicineType : MedicineType,
)