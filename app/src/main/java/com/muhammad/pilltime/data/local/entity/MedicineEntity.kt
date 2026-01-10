package com.muhammad.pilltime.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.muhammad.pilltime.domain.model.MedicineFrequency
import com.muhammad.pilltime.domain.model.MedicineType

@Entity(indices = [Index("medicineGroupId")], tableName = "MedicineEntity")
data class MedicineEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val medicineGroupId : Long,
    val name : String,
    val dosage : Int,
    val frequency : MedicineFrequency,
    val startDate : Long,
    val endDate : Long,
    val medicineTaken : Boolean,
    val medicineTime : Int,
    val createdAt : Long,
    val medicineType : MedicineType,
)