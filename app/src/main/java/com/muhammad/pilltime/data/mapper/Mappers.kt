package com.muhammad.pilltime.data.mapper

import com.muhammad.pilltime.data.local.entity.MedicineEntity
import com.muhammad.pilltime.domain.model.Medicine
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atDate
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun MedicineEntity.toMedicine(): Medicine {
    val timezone = TimeZone.currentSystemDefault()
    return Medicine(
        id = id,
        name = name,
        dosage = dosage,
        frequency = frequency,
        medicineTaken = medicineTaken,
        startDate = startDate?.let { Instant.fromEpochMilliseconds(it) }
            ?.toLocalDateTime(timezone)?.date,
        endDate = Instant.fromEpochMilliseconds(endDate).toLocalDateTime(timezone).date,
        medicineTime = Instant.fromEpochMilliseconds(medicineTime).toLocalDateTime(timezone).time,
        medicineType = medicineType, createdAt = Instant.fromEpochMilliseconds(createdAt)
    )
}

fun Medicine.toMedicineEntity(): MedicineEntity {
    val timezone = TimeZone.currentSystemDefault()
    return MedicineEntity(
        id = id,
        name = name,
        dosage = dosage,
        frequency = frequency,
        startDate = startDate?.atTime(medicineTime)?.toInstant(timezone)?.toEpochMilliseconds(),
        endDate = endDate.atTime(medicineTime).toInstant(timezone).toEpochMilliseconds(),
        medicineTaken = medicineTaken,
        medicineTime = medicineTime.atDate(endDate).toInstant(timezone).toEpochMilliseconds(),
        createdAt = createdAt.toEpochMilliseconds(),
        medicineType = medicineType
    )
}