package com.muhammad.pilltime.domain.repository

import com.muhammad.pilltime.domain.model.ScheduleHistory
import kotlinx.coroutines.flow.Flow

interface ScheduleHistoryRepository {
    suspend fun insertScheduleHistory(scheduleHistory : ScheduleHistory)
    fun getSchedulesHistory() : Flow<List<ScheduleHistory>>
}