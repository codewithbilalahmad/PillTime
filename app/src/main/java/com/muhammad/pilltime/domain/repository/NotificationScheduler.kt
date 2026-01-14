package com.muhammad.pilltime.domain.repository



interface NotificationScheduler {
    suspend fun scheduleNextReminder()
    suspend fun cancelAll()
}