package com.muhammad.pilltime.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Immutable
@Serializable
data class Medicine(
    val id : Long,
    val name : String,
    val dosage : Int,
    val frequency : MedicineFrequency,
    val startDate : LocalDate,
    val endDate : LocalDate,
    val showMedicineSchedule : Boolean = false,
    @Contextual
    val createdAt : Instant,
    val schedules : List<MedicineSchedule>,
    val medicineType : MedicineType
)
