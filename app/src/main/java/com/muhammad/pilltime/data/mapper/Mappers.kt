package com.muhammad.pilltime.data.mapper

import com.muhammad.pilltime.data.local.entity.MedicineEntity
import com.muhammad.pilltime.data.local.entity.MedicineScheduleEntity
import com.muhammad.pilltime.domain.model.Medicine
import com.muhammad.pilltime.domain.model.MedicineSchedule
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun MedicineEntity.toMedicine(schedules: List<MedicineSchedule>): Medicine {
    val timezone = TimeZone.currentSystemDefault()
    return Medicine(
        id = id,
        name = name,
        dosage = dosage,
        frequency = frequency,
        startDate = Instant.fromEpochMilliseconds(startDate)
            .toLocalDateTime(timezone).date,
        endDate = Instant.fromEpochMilliseconds(endDate).toLocalDateTime(timezone).date,
        medicineType = medicineType,
        createdAt = Instant.fromEpochMilliseconds(createdAt), schedules = schedules
    )
}

fun Medicine.toMedicineEntity(): MedicineEntity {
    val timezone = TimeZone.currentSystemDefault()
    val startMillis = startDate.atStartOfDayIn(timezone).toEpochMilliseconds()
    val endMillis = endDate.atStartOfDayIn(timezone).toEpochMilliseconds()
    return MedicineEntity(
        id = id,
        name = name,
        dosage = dosage,
        frequency = frequency,
        startDate = startMillis,
        endDate = endMillis,
        createdAt = createdAt.toEpochMilliseconds(),
        medicineType = medicineType
    )
}

fun MedicineScheduleEntity.toMedicineSchedule(): MedicineSchedule {
    return MedicineSchedule(
        id = id, time = LocalTime(
            hour = medicineTime / 60,
            minute = medicineTime % 60
        ), status = status, medicineId = medicineId
    )
}

fun MedicineSchedule.toMedicineScheduleEntity(): MedicineScheduleEntity {
    return MedicineScheduleEntity(
        id = id,
        status = status,
        medicineTime = time.hour * 60 + time.minute,
        medicineId = medicineId
    )
}