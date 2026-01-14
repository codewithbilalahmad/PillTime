package com.muhammad.pilltime.domain.repository

import com.muhammad.pilltime.domain.model.MedicineSchedule
import com.muhammad.pilltime.domain.model.ScheduleStatus
import kotlinx.coroutines.flow.Flow

interface MedicationScheduleRespository {
    suspend fun insertMedicineSchedules(schedules: List<MedicineSchedule>)
    fun getMedicineSchedules(medicineId: Long): Flow<List<MedicineSchedule>>
    fun getAllSchedules(): Flow<List<MedicineSchedule>>
    suspend fun updateMedicineScheduleStatus(status : ScheduleStatus, scheduleId : Long)
    suspend fun deleteMedicineSchedulesByMedicineId(medicineId: Long)
    fun getScheduleById(scheduleId: Long): Flow<MedicineSchedule?>
}