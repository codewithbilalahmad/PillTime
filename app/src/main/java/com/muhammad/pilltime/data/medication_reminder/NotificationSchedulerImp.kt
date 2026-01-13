package com.muhammad.pilltime.data.medication_reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.muhammad.pilltime.domain.model.Medicine
import com.muhammad.pilltime.domain.repository.MedicationScheduleRespository
import com.muhammad.pilltime.domain.repository.NotificationScheduler
import com.muhammad.pilltime.utils.Constants.DOSE
import com.muhammad.pilltime.utils.Constants.MEDICINE_ID
import com.muhammad.pilltime.utils.Constants.MEDICINE_NAME
import com.muhammad.pilltime.utils.Constants.MEDICINE_TYPE
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
    override fun scheduleMedicine(medicine: Medicine) {
        val timeZone = TimeZone.currentSystemDefault()
        val now = Clock.System.now()
        medicine.schedules.forEach { schedule ->
            val scheduleDate = schedule.date ?: return@forEach
            val triggerInstant = scheduleDate
                .atTime(schedule.time)
                .toInstant(timeZone)
            val triggerMillis = triggerInstant.toEpochMilliseconds()
            if (triggerMillis <= now.toEpochMilliseconds()) return@forEach
            val intent = Intent(context, MedicineReminderReceiver::class.java).apply {
                putExtra(MEDICINE_ID, medicine.id)
                putExtra(MEDICINE_NAME, medicine.name)
                putExtra(DOSE, medicine.dosage)
                putExtra(MEDICINE_TYPE, medicine.medicineType.name)
                putExtra(SCHEDULE_ID, schedule.id)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                schedule.id.toInt(),
                intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (!alarmManager.canScheduleExactAlarms()) {
                        return@forEach
                    }
                }
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerMillis,
                    pendingIntent
                )
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun cancelMedicine(medicineId: Long) {
        val schedules = medicationScheduleRepository.getMedicineSchedules(medicineId).first()
        schedules.forEach{schedule ->
            val intent = Intent(context, MedicineReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode(schedule.id),
                intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
        }
    }

    private fun requestCode(scheduleId: Long): Int {
        return scheduleId.hashCode()
    }
}