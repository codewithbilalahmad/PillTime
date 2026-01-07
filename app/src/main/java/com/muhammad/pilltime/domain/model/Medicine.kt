package com.muhammad.pilltime.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Immutable
@Serializable
data class Medicine(
    val id : Long,
    val name : String,
    val dosage : Int,
    val frequency : String,
    val startDate : LocalDate?,
    val endDate : LocalDate,
    val medicineTaken : Boolean,
    val medicineTime : LocalTime,
    @Contextual
    val createdAt : Instant,
    val medicineType : MedicineType
)
