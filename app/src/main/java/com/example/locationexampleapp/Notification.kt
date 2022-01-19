package com.example.locationexampleapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

fun createNotificationChannel(context: Context) = run {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel("CHANNEL_ID", "channel_name", NotificationManager.IMPORTANCE_HIGH)
        channel.description = "Location Notification Channel"
        val notificationManager = context.getSystemService(
            NotificationManager::class.java
        )
        //Creat notification channel
        notificationManager.createNotificationChannel(channel)
    }
}

fun createNotification(context: Context): Notification{
    return NotificationCompat.Builder(context, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Location Service")
        .setContentText("Your location is being tracked")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()
}