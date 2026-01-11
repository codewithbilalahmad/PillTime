package com.muhammad.pilltime.data.medication_reminder

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.muhammad.pilltime.utils.Constants.DOSE
import com.muhammad.pilltime.utils.Constants.MEDICINE_ID
import com.muhammad.pilltime.utils.Constants.MEDICINE_NAME
import com.muhammad.pilltime.utils.Constants.MEDICINE_TYPE
import com.muhammad.pilltime.utils.Constants.SCHEDULE_ID

class MedicineReminderWorker(
    context: Context,
    params: WorkerParameters,
) : Worker(context, params) {
    override fun doWork(): Result {
        val medicineId = inputData.getLong(MEDICINE_ID, -1L)
        val medicineName = inputData.getString(MEDICINE_NAME) ?: return Result.failure()
        val dose = inputData.getInt(DOSE, 1)
        val medicineType = inputData.getString(MEDICINE_TYPE) ?: return Result.failure()
        val scheduleId = inputData.getLong(SCHEDULE_ID,-1L)
        MedicationReminderHelper.createMedicationReminderNotification(
            context = applicationContext,
            medicineName = medicineName,
            dose = dose, scheduleId = scheduleId,
            medicineType = medicineType, medicineId = medicineId
        )
        return Result.success()
    }
}