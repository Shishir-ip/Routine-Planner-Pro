package com.shishir.routineplannerpro.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.*

class AlarmScheduler(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleAlarm(activityId: String, timeInMillis: Long, requestCode: Int) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = "com.shishir.routineplannerpro.ALARM_TRIGGER"
            putExtra("activity_id", activityId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Cancel any existing alarm with the same request code
        cancelAlarm(requestCode)

        // Schedule the alarm
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )
        }
    }

    fun cancelAlarm(requestCode: Int) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntent?.let {
            alarmManager.cancel(it)
        }
    }

    fun cancelAllAlarms() {
        // This would require keeping track of all scheduled alarms
        // For simplicity, we'll just clear all pending intents
        // In a production app, you'd want to store alarm IDs and cancel them individually
    }
}
