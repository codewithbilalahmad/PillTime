package com.muhammad.pilltime.data.medication_reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.muhammad.pilltime.MainActivity
import com.muhammad.pilltime.R
import com.muhammad.pilltime.domain.model.MedicineType
import com.muhammad.pilltime.utils.Constants.REMINDER_ACTIVITY_REQUEST_CODE
import com.muhammad.pilltime.utils.Constants.REMINDER_CHANNEL_DESC
import com.muhammad.pilltime.utils.Constants.REMINDER_CHANNEL_ID
import com.muhammad.pilltime.utils.Constants.REMINDER_CHANNEL_NAME

fun createMedicationReminderNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            REMINDER_CHANNEL_ID,
            REMINDER_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = REMINDER_CHANNEL_DESC
            enableVibration(true)
        }
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}

fun createMedicationReminderNotification(
    context: Context,
    medicineName: String,
    dose: Int, medicineType: String,
) {
    createMedicationReminderNotificationChannel(context)
    val notificationId = System.currentTimeMillis().toInt()
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val activityIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val bigIcon = MedicineType.valueOf(medicineType.uppercase()).icon
    val activityPendingIntent = PendingIntent.getActivity(
        context,
        REMINDER_ACTIVITY_REQUEST_CODE,
        activityIntent,
        PendingIntent.FLAG_IMMUTABLE
    )
    val builder = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID).setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("Medication Reminder")
            .setContentIntent(activityPendingIntent)
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    "Time to take $dose Dose of $medicineName $medicineType"
                )
            )
            .setLargeIcon(BitmapFactory.decodeResource(context.resources,bigIcon))
            .setContentText("Time to take $dose Dose of $medicineName $medicineType").setPriority(
                NotificationCompat.PRIORITY_HIGH
            ).setCategory(NotificationCompat.CATEGORY_REMINDER).setAutoCancel(true)
    notificationManager.notify(notificationId, builder.build())
}