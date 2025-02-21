package com.example.notifications

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    val idChannel:String = "Chanel01"
    lateinit var notification:NotificationCompat.Builder
    lateinit var btnNotification:Button

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

    private fun createNotificationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName:String = "Notification"
            val channelDescription:String = "Notification Channel for \"Notifications\" App"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(idChannel, channelName, importance).apply {
                description = channelDescription
            }

            val notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification() {
        notification = NotificationCompat.Builder(this, idChannel)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("My Notification")
            .setContentText("Notification content")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    @SuppressLint("MissingPermission")
    private fun sendNotification() {
        with(NotificationManagerCompat.from(this)) {
            notify(1, notification.build())
        }
    }
}