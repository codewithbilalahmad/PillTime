package com.muhammad.pilltime.data.medication_reminder

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.muhammad.pilltime.domain.model.Medicine
import com.muhammad.pilltime.domain.repository.NotificationScheduler
import com.muhammad.pilltime.utils.Constants.DOSE
import com.muhammad.pilltime.utils.Constants.MEDICINE_ID
import com.muhammad.pilltime.utils.Constants.MEDICINE_NAME
import com.muhammad.pilltime.utils.Constants.MEDICINE_TYPE
import com.muhammad.pilltime.utils.Constants.SCHEDULE_ID
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import java.util.concurrent.TimeUnit
import kotlin.time.Clock

class NotificationSchedulerImp(
    private val context: Context,
) : NotificationScheduler {
    override fun scheduleMedicine(medicine: Medicine) {
        val timeZone = TimeZone.currentSystemDefault()
        val now = Clock.System.now()
        medicine.schedules.forEach { schedule ->
            val scheduleDate = schedule.date ?: return@forEach
            val triggerInstant = scheduleDate
                .atTime(schedule.time)
                .toInstant(timeZone)
            val delayMillis = (triggerInstant - now).inWholeMilliseconds
            if (delayMillis > 0) {
                enqueueWorker(
                    medicine = medicine,
                    scheduleId = schedule.id,
                    delayMillis = delayMillis
                )
            }
        }
    }

    private fun enqueueWorker(
        medicine: Medicine,
        scheduleId: Long,
        delayMillis: Long,
    ) {
        val data = Data.Builder().putLong(MEDICINE_ID, medicine.id).putString(MEDICINE_NAME, medicine.name)
                .putInt(DOSE, medicine.dosage).putLong(SCHEDULE_ID, scheduleId).putString(
                    MEDICINE_TYPE,
                    medicine.medicineType.name.lowercase().replaceFirstChar { it.uppercase() })
                .build()
        val request = OneTimeWorkRequestBuilder<MedicineReminderWorker>().setInitialDelay(
            delayMillis,
            TimeUnit.MILLISECONDS
        ).setInputData(data).addTag("medicine_${medicine.id}").build()
        WorkManager.getInstance(context).enqueue(
            request
        )
    }

    override fun cancelMedicine(medicineId: Long) {
        WorkManager.getInstance(context).cancelAllWorkByTag("medicine_$medicineId")
    }
}