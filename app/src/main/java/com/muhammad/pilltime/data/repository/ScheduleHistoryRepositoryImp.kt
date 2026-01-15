package com.muhammad.pilltime.data.repository

import com.muhammad.pilltime.data.local.dao.ScheduleHistoryDao
import com.muhammad.pilltime.data.mapper.toScheduleHistory
import com.muhammad.pilltime.data.mapper.toScheduleHistoryEntity
import com.muhammad.pilltime.domain.model.ScheduleHistory
import com.muhammad.pilltime.domain.repository.ScheduleHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ScheduleHistoryRepositoryImp(
    private val scheduleHistoryDao: ScheduleHistoryDao,
) : ScheduleHistoryRepository {
    override suspend fun insertScheduleHistory(scheduleHistory: ScheduleHistory) {
        scheduleHistoryDao.insertScheduleHistory(scheduleHistory.toScheduleHistoryEntity())
    }

    override fun getSchedulesHistory(): Flow<List<ScheduleHistory>> {
        return scheduleHistoryDao.getSchedulesHistory().map {entities ->
            entities.map { it.toScheduleHistory() }
        }
    }
}