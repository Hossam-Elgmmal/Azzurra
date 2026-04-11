package com.iti.azzurra.data.work

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.iti.azzurra.MainActivity
import com.iti.azzurra.R
import com.iti.azzurra.utils.Constants.ERROR_TAG
import com.iti.azzurra.utils.hasNotificationPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.jvm.java

class NotificationController @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    val channelId = "channel_id"

    fun makeNotificationChannel() {
        val name = context.getString(R.string.notification_channel_name)
        val descriptionText = context.getString(R.string.notification_channel_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        NotificationManagerCompat.from(context)
            .createNotificationChannel(channel)
    }

    @SuppressLint("MissingPermission")
    fun sendNotification(title: String, contentText: String) {

        if (!context.hasNotificationPermission()) {
            Log.e(ERROR_TAG, "sendNotification: No Permission")
            return
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent
            .getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification_logo)
            .setContentTitle(title)
            .setContentText(contentText)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(1, notification)
    }
}