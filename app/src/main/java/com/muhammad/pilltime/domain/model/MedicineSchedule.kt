package com.muhammad.pilltime.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import java.util.UUID

@Immutable
@Serializable
data class MedicineSchedule(
    val id : Long = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE,
    val medicineId : Long,
    val time : LocalTime,
    val status : ScheduleStatus = ScheduleStatus.PENDING
)
