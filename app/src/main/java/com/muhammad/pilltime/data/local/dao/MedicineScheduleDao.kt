package com.muhammad.pilltime.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.muhammad.pilltime.data.local.entity.MedicineScheduleEntity
import com.muhammad.pilltime.domain.model.ScheduleStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineScheduleDao {
    @Insert
    suspend fun insertMedicineSchedules(schedules: List<MedicineScheduleEntity>)

    @Query("SELECT * FROM MedicineScheduleEntity WHERE medicineId=:medicineId")
    fun getMedicineSchedules(medicineId: Long): Flow<List<MedicineScheduleEntity>>

    @Query("SELECT * FROM MedicineScheduleEntity WHERE id=:scheduleId")
    fun getScheduleById(scheduleId: Long): Flow<MedicineScheduleEntity?>

    @Query("SELECT * FROM MedicineScheduleEntity")
    fun getAllSchedules(): Flow<List<MedicineScheduleEntity>>


    @Query(
        "UPDATE MedicineScheduleEntity SET status =:status WHERE id =:scheduleId"
    )
    suspend fun updateMedicineScheduleStatus(status : ScheduleStatus, scheduleId : Long)
    @Query(
        "DELETE FROM MedicineScheduleEntity WHERE medicineId =:medicineId"
    )
    suspend fun deleteMedicineSchedulesByMedicineId(medicineId : Long)
}