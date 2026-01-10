package com.muhammad.pilltime.data.medication_reminder

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.muhammad.pilltime.domain.model.Medicine
import com.muhammad.pilltime.domain.model.toDatePeriod
import com.muhammad.pilltime.domain.repository.NotificationScheduler
import com.muhammad.pilltime.utils.Constants.DOSE
import com.muhammad.pilltime.utils.Constants.MEDICINE_NAME
import com.muhammad.pilltime.utils.Constants.MEDICINE_TYPE
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import java.util.concurrent.TimeUnit
import kotlin.time.Clock

class NotificationSchedulerImp(
    private val context: Context,
) : NotificationScheduler {
    override fun scheduleMedicine(medicine: Medicine) {
        val timeZone = TimeZone.currentSystemDefault()
        val now = Clock.System.now()
        var date = medicine.startDate

        while (date <= medicine.endDate) {

            medicine.schedules.forEach { schedule ->
                val triggerInstant = date
                    .atTime(schedule.time)
                    .toInstant(timeZone)
                val delayMillis = (triggerInstant - now).inWholeMilliseconds
                if (delayMillis > 0) {
                    enqueueWorker(
                       medicine =  medicine,
                        delayMillis = delayMillis
                    )
                }
            }
            date += medicine.frequency.toDatePeriod()
        }
    }

    private fun enqueueWorker(
        medicine: Medicine,
        delayMillis: Long,
    ) {
        val data = Data.Builder().putString(MEDICINE_NAME, medicine.name)
                .putInt(DOSE, medicine.dosage).putString(
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