package com.muhammad.pilltime.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.muhammad.pilltime.data.local.entity.ScheduleHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleHistoryDao{
    @Insert
    suspend fun insertScheduleHistory(scheduleHistoryEntity: ScheduleHistoryEntity)
    @Query("SELECT * FROM ScheduleHistoryEntity ORDER BY date DESC")
     fun getSchedulesHistory() : Flow<List<ScheduleHistoryEntity>>
}