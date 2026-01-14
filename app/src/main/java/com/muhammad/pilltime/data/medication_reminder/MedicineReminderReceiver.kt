package com.muhammad.pilltime.data.medication_reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.muhammad.pilltime.domain.repository.MedicationRepository
import com.muhammad.pilltime.domain.repository.MedicationScheduleRespository
import com.muhammad.pilltime.domain.repository.NotificationScheduler
import com.muhammad.pilltime.utils.Constants.MEDICINE_ID
import com.muhammad.pilltime.utils.Constants.SCHEDULE_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class MedicineReminderReceiver : BroadcastReceiver(), KoinComponent{
    private val medicineRepository : MedicationRepository by inject()
    private val scheduleRepository : MedicationScheduleRespository by inject()
    private val notificationScheduler : NotificationScheduler by inject()
    override fun onReceive(context: Context, intent: Intent) {
        val medicineId = intent.getLongExtra(MEDICINE_ID, -1L)
        val scheduleId = intent.getLongExtra(SCHEDULE_ID,-1L)
        if(medicineId == -1L && scheduleId == -1L) return
        CoroutineScope(Dispatchers.IO).launch {
            val medicine = medicineRepository.getMedicineById(medicineId).firstOrNull() ?: return@launch
            val schedule =  scheduleRepository.getScheduleById(scheduleId).firstOrNull() ?: return@launch
            MedicationReminderHelper.createMedicationReminderNotification(
                context = context,
                medicine = medicine,
                schedule = schedule
            )
            notificationScheduler.scheduleNextReminder()
        }
    }
}