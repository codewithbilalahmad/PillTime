package com.muhammad.pilltime.data.medication_reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.muhammad.pilltime.MainActivity
import com.muhammad.pilltime.PillTimeApplication
import com.muhammad.pilltime.R
import com.muhammad.pilltime.domain.model.MedicineType
import com.muhammad.pilltime.utils.Constants.DONE_REMINDER_ACTION
import com.muhammad.pilltime.utils.Constants.MEDICINE_ID
import com.muhammad.pilltime.utils.Constants.MISSED_REMINDER_ACTION
import com.muhammad.pilltime.utils.Constants.NOTIFICATION_ID
import com.muhammad.pilltime.utils.Constants.REMINDER_ACTIVITY_REQUEST_CODE
import com.muhammad.pilltime.utils.Constants.REMINDER_CHANNEL_DESC
import com.muhammad.pilltime.utils.Constants.REMINDER_CHANNEL_ID
import com.muhammad.pilltime.utils.Constants.REMINDER_CHANNEL_NAME
import com.muhammad.pilltime.utils.Constants.SCHEDULE_ID

object MedicationReminderHelper {
    private val context = PillTimeApplication.INSTANCE
    fun createMedicationReminderNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val soundUri =
                "android:resources://${context.packageName}/${R.raw.notification}".toUri()
            val audioAttributes =
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).setContentType(
                    AudioAttributes.CONTENT_TYPE_SONIFICATION
                ).build()
            val channel = NotificationChannel(
                REMINDER_CHANNEL_ID,
                REMINDER_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = REMINDER_CHANNEL_DESC
                enableVibration(true)
                setSound(soundUri, audioAttributes)
            }
            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun createMedicationReminderNotification(
        context: Context,
        medicineName: String, scheduleId: Long, medicineId: Long,
        dose: Int, medicineType: String,
    ) {
        createMedicationReminderNotificationChannel(context)
        val notificationId = System.currentTimeMillis().toInt()
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val activityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val type = MedicineType.valueOf(medicineType.uppercase())
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            REMINDER_ACTIVITY_REQUEST_CODE,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val dosageUnit = when (type) {
            MedicineType.TABLET, MedicineType.CAPSULE -> "tablet"
            MedicineType.SYRUP -> "spoon"
            MedicineType.DROPS -> "drops"
            MedicineType.Spray -> "spray"
            MedicineType.GEL -> "application"
        }
        val builder = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("$medicineName Reminder")
            .setContentIntent(activityPendingIntent)
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    "Time to take $dose $dosageUnit of $medicineName ${
                        medicineType.lowercase().replaceFirstChar { it.uppercase() }
                    }"
                )
            )
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, type.icon))
            .setContentText("Time to take $dose Dose of $medicineName $medicineType").setPriority(
                NotificationCompat.PRIORITY_HIGH
            ).addAction(
                R.drawable.ic_done,
                "Done",
                reminderDonePendingIntent(
                    scheduleId = scheduleId,
                    medicineId = medicineId,
                    notificationId = notificationId
                )
            )
            .addAction(
                R.drawable.ic_missed,
                "Missed",
                reminderMissedPendingIntent(
                    scheduleId = scheduleId,
                    medicineId = medicineId,
                    notificationId = notificationId
                )
            )
            .setCategory(
                NotificationCompat.CATEGORY_REMINDER
            ).setAutoCancel(true)
        notificationManager.notify(notificationId, builder.build())
    }

    fun reminderDonePendingIntent(
        scheduleId: Long,
        medicineId: Long,
        notificationId: Int,
    ): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            action = DONE_REMINDER_ACTION
            putExtra(MEDICINE_ID, medicineId)
            putExtra(SCHEDULE_ID, scheduleId)
            putExtra(NOTIFICATION_ID, notificationId)
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        return PendingIntent.getActivity(
            context,
            scheduleId.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    fun reminderMissedPendingIntent(
        medicineId: Long,
        scheduleId: Long,
        notificationId: Int,
    ): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            action = MISSED_REMINDER_ACTION
            putExtra(SCHEDULE_ID, scheduleId)
            putExtra(MEDICINE_ID, medicineId)
            putExtra(NOTIFICATION_ID, notificationId)
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        return PendingIntent.getActivity(
            context,
            scheduleId.toInt() + 100,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}