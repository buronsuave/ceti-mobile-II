package com.example.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    private val idChannel:String = "Channel01"
    private lateinit var notification:NotificationCompat.Builder
    private lateinit var btnNotification:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnNotification = findViewById(R.id.btnNotification)
        createNotificationChanel()

        btnNotification.setOnClickListener {
            createNotification()
            sendNotification()
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun createNotificationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Notification"
            val channelDescription = "Notification Channel for \"Notifications\" App"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(idChannel, channelName, importance).apply {
                description = channelDescription
            }

            val notificationManager:NotificationManager = getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun createNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE
            else 0

        val pendingIntent : PendingIntent = PendingIntent.getActivity(this, 0, intent, flag)
        notification = NotificationCompat.Builder(this, idChannel)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("My Notification")
            .setContentText("Notification content")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("The information on \"bigText\" is shown as an "
                        + "extended notification. The notification can be expanded "
                        + "to show its content. This is just a sample text, please "
                        + "modified the source code in order to show a custom message."))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    @SuppressLint("MissingPermission")
    private fun sendNotification() {
        with(NotificationManagerCompat.from(this)) {
            notify(1, notification.build())
        }
    }
}