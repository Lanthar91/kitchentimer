package com.example.timer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.timer.prefs.PreferencesRepository
import kotlinx.coroutines.flow.first

suspend fun showFinishNotification(context: Context, prefs: PreferencesRepository) {
    val soundUri = prefs.soundUri.first()?.let { Uri.parse(it) }
    val vibrationEnabled = prefs.vibrationEnabled.first()
    val channelId = "timer"
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(channelId, "Timer", NotificationManager.IMPORTANCE_DEFAULT).apply {
            if (soundUri != null) {
                val attrs = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()
                setSound(soundUri, attrs)
            }
        }
        manager.createNotificationChannel(channel)
    }
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle(context.getString(R.string.app_name))
        .setContentText("Timer finished")
        .setAutoCancel(true)
    with(NotificationManagerCompat.from(context)) { notify(1, builder.build()) }
    if (vibrationEnabled) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(500)
        }
    }
}
