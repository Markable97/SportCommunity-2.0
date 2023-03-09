package com.glushko.sportcommunity.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.glushko.sportcommunity.R
import com.glushko.sportcommunity.presentation.main_screen.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MessagingService : FirebaseMessagingService() {

    companion object {
        private const val NOTIFICATION_FIELD_TITLE = "gcm.notification.title"
        private const val NOTIFICATION_FIELD_BODY = "gcm.notification.body"
    }

    override fun onNewToken(token: String) {
        Timber.e("onNewToken = $token")
    }

    override fun handleIntent(intent: Intent?) {
        val title = intent?.getStringExtra(NOTIFICATION_FIELD_TITLE)
        val body = intent?.getStringExtra(NOTIFICATION_FIELD_BODY)
        if (title != null && body != null) {
            addNotification(title, body, intent)
        }
    }

    private fun addNotification(title: String, body: String, dataIntent: Intent) {
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(body)
            )
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    0,
                    Intent(this, MainActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setSound(defaultSoundUri)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            channelId,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = ""
            enableLights(true)
        }

        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(1, notificationBuilder.build())
    }
}
