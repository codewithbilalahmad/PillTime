package com.muhammad.pilltime.data.medication_reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.muhammad.pilltime.utils.Constants.DOSE
import com.muhammad.pilltime.utils.Constants.MEDICINE_ID
import com.muhammad.pilltime.utils.Constants.MEDICINE_NAME
import com.muhammad.pilltime.utils.Constants.MEDICINE_TYPE
import com.muhammad.pilltime.utils.Constants.SCHEDULE_ID


class MedicineReminderReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        val medicineId = intent.getLongExtra(MEDICINE_ID, -1L)
        val medicineName = intent.getStringExtra(MEDICINE_NAME) ?: return
        val dose = intent.getIntExtra(DOSE, 1)
        val medicineType = intent.getStringExtra(MEDICINE_TYPE) ?: return
        val scheduleId = intent.getLongExtra(SCHEDULE_ID,-1L)
        MedicationReminderHelper.createMedicationReminderNotification(
            context = context,
            medicineName = medicineName,
            dose = dose, scheduleId = scheduleId,
            medicineType = medicineType, medicineId = medicineId
        )
    }
}