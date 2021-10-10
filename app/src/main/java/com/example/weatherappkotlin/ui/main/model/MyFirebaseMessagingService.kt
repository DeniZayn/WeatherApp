package com.example.weatherappkotlin.ui.main.model

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.weatherappkotlin.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        //Log.d("MyFMessagingService", "MyFMessagingService = ${remoteMessage.data}")
        val remoteMessageData = remoteMessage.data
        if (remoteMessageData.isNotEmpty()) {
            handleDataMessage(remoteMessageData)
        }
    }

    private fun handleDataMessage(remoteMessageData: Map<String, String>) {
        val title = remoteMessageData[PUSH_KEY_TITLE]
        val message = remoteMessageData[PUSH_KEY_MESSAGE]

        if (!title.isNullOrBlank() && message.isNullOrBlank()){

             val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                 .setContentTitle(title)
                 .setContentText(message)
                 .setSmallIcon(R.drawable.ic_message)
                 .setColor(Color.BLUE)
                 .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(
                    NotificationChannel(CHANNEL_ID,
                "First Channel", NotificationManager.IMPORTANCE_DEFAULT).apply {
                    description = "Description"
                    })
            }
            notificationManager.notify(NOTIFICATION_ID, notification.build())
        }
    }


 //   override fun onNewToken(token: String) {
 //       super.onNewToken(token)
 //       Log.d(TAG, "onNewToken = $token")
 //   }


    companion object{
        private const val PUSH_KEY_TITLE = "title"
        private const val PUSH_KEY_MESSAGE = "message"
        private const val CHANNEL_ID = "channel_id"
        private const val NOTIFICATION_ID = 37

    }

    @SuppressLint("LongLogTag")
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("MyFMessagingService", "onNewToken = $token")
    }
}