package com.muhammad.pilltime.data.mapper

import com.muhammad.pilltime.data.local.entity.MedicineEntity
import com.muhammad.pilltime.domain.model.Medicine
import com.muhammad.pilltime.domain.model.Schedule
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun List<MedicineEntity>.toMedicine(): Medicine {
    val timezone = TimeZone.currentSystemDefault()
    val first = first()
    return Medicine(
        id = first.medicineGroupId,
        name = first.name,
        dosage = first.dosage,
        frequency = first.frequency,
        medicineTaken = first.medicineTaken,
        startDate = Instant.fromEpochMilliseconds(first.startDate)
            .toLocalDateTime(timezone).date,
        endDate = Instant.fromEpochMilliseconds(first.endDate).toLocalDateTime(timezone).date,
        medicineType = first.medicineType,
        createdAt = Instant.fromEpochMilliseconds(first.createdAt), schedules = map {schedule->
            Schedule(
                time = LocalTime(
                    hour = schedule.medicineTime / 60,
                    minute = schedule.medicineTime % 60,
                )
            )
        }
    )
}

fun Medicine.toMedicineEntities(): List<MedicineEntity> {
    val timezone = TimeZone.currentSystemDefault()
    val startMillis = startDate.atStartOfDayIn(timezone).toEpochMilliseconds()
    val endMillis = endDate.atStartOfDayIn(timezone).toEpochMilliseconds()
    return schedules.map { schedule ->
         MedicineEntity(
            medicineGroupId = id,
            name = name,
            dosage = dosage,
            frequency = frequency,
            startDate =startMillis,
            endDate = endMillis,
            medicineTaken = medicineTaken,
            medicineTime = schedule.time.hour * 60 + schedule.time.minute,
            createdAt = createdAt.toEpochMilliseconds(),
            medicineType = medicineType
        )
    }
}