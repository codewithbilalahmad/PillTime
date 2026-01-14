package com.muhammad.pilltime.data.medication_reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.muhammad.pilltime.domain.repository.MedicationScheduleRespository
import com.muhammad.pilltime.domain.repository.NotificationScheduler
import com.muhammad.pilltime.utils.Constants.MEDICINE_ID
import com.muhammad.pilltime.utils.Constants.REMINDER_ALARM_REQUEST_CODE
import com.muhammad.pilltime.utils.Constants.SCHEDULE_ID
import kotlinx.coroutines.flow.first
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlin.time.Clock

class NotificationSchedulerImp(
    private val context: Context,
    private val medicationScheduleRepository: MedicationScheduleRespository,
) : NotificationScheduler {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    override suspend fun scheduleNextReminder() {
        cancelAll()
        val schedules = medicationScheduleRepository.getAllSchedules().first()
        val timeZone = TimeZone.currentSystemDefault()
        val now = Clock.System.now()
        val nextSchedule = schedules.mapNotNull {schedule ->
            schedule.date?.let { date ->
                schedule to date.atTime(schedule.time).toInstant(timeZone)
            }
        }.filter { it.second > now }.minByOrNull { it.second } ?: return
        val schedule = nextSchedule.first
        val triggerMillis = nextSchedule.second.toEpochMilliseconds()
        val intent = Intent(context, MedicineReminderReceiver::class.java).apply {
            putExtra(MEDICINE_ID, schedule.medicineId)
            putExtra(SCHEDULE_ID, schedule.id)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REMINDER_ALARM_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) return
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerMillis,
            pendingIntent
        )
    }

    override suspend fun cancelAll() {
        val intent = Intent(context, MedicineReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REMINDER_ALARM_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }
}