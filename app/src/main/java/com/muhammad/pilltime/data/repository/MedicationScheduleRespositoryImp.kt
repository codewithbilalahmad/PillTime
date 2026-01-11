package com.muhammad.pilltime.data.repository

import com.muhammad.pilltime.data.local.dao.MedicineScheduleDao
import com.muhammad.pilltime.data.mapper.toMedicineSchedule
import com.muhammad.pilltime.data.mapper.toMedicineScheduleEntity
import com.muhammad.pilltime.domain.model.MedicineSchedule
import com.muhammad.pilltime.domain.model.ScheduleStatus
import com.muhammad.pilltime.domain.repository.MedicationScheduleRespository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MedicationScheduleRespositoryImp(
    private val medicineScheduleDao: MedicineScheduleDao,
) : MedicationScheduleRespository {
    override suspend fun insertMedicineSchedules(
        schedules: List<MedicineSchedule>,
    ) {
        medicineScheduleDao.insertMedicineSchedules(
            schedules = schedules.map { schedule -> schedule.toMedicineScheduleEntity() },
        )
    }

    override fun getMedicineSchedules(medicineId: Long): Flow<List<MedicineSchedule>> {
        return medicineScheduleDao.getMedicineSchedules(medicineId).map { entities ->
            entities.map { entity -> entity.toMedicineSchedule() }
        }
    }

    override suspend fun updateMedicineScheduleStatus(
        status: ScheduleStatus,
        scheduleId: Long,
    ) {
        medicineScheduleDao.updateMedicineScheduleStatus(status = status, scheduleId = scheduleId)
    }
}