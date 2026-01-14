package com.muhammad.pilltime.data.medication_reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.muhammad.pilltime.domain.repository.NotificationScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BootReceiver : BroadcastReceiver(), KoinComponent{
    private val schedule : NotificationScheduler by inject()
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(Dispatchers.IO).launch {
                schedule.scheduleNextReminder()
            }
        }
    }
}