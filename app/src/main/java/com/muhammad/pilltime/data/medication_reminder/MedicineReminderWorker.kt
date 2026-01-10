package com.muhammad.pilltime.data.medication_reminder

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.muhammad.pilltime.utils.Constants.DOSE
import com.muhammad.pilltime.utils.Constants.MEDICINE_NAME
import com.muhammad.pilltime.utils.Constants.MEDICINE_TYPE

class MedicineReminderWorker(
    context: Context,
    params: WorkerParameters,
) : Worker(context, params) {
    override fun doWork(): Result {
        val medicineName = inputData.getString(MEDICINE_NAME) ?: return Result.failure()
        val dose = inputData.getInt(DOSE, 1)
        val medicineType = inputData.getString(MEDICINE_TYPE) ?: return Result.failure()
        println("Worker Medicine Name : $medicineName")
        println("Worker Medicine Dose : $dose")
        println("Worker Medicine Type : $medicineType")
        createMedicationReminderNotification(
            context = applicationContext,
            medicineName = medicineName,
            dose = dose,
            medicineType =medicineType
        )
        return Result.success()
    }
}