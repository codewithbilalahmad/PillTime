package com.muhammad.pilltime.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.muhammad.pilltime.domain.model.MedicineFrequency
import com.muhammad.pilltime.domain.model.MedicineType

@Entity
data class MedicineEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val name : String,
    val dosage : Int,
    val frequency : MedicineFrequency,
    val startDate : Long,
    val endDate : Long,
    val createdAt : Long,
    val medicineType : MedicineType,
)