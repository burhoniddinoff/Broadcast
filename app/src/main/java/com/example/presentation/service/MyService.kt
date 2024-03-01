package com.example.presentation.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.broadcast.R
import com.example.presentation.activity.MainActivity
import com.example.presentation.broadcast.MyReceiver

class MyService : Service() {

    private val myReceiver = MyReceiver()
    private val notificationChannelId = "DEMO"

    override fun onCreate() {
        super.onCreate()
        createChannel()
        startMyService()

//        val intentFilter = IntentFilter()
//        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
//        registerReceiver(myReceiver, intentFilter)
//
//        myReceiver.bluetoothClick = {
//            playNotificationSound(this)
//        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TTT", "MyService -> onStartCommand ")
        val intentFilter = IntentFilter().apply {
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        }
        registerReceiver(myReceiver, intentFilter)

        myReceiver.bluetoothClick = {
            playNotificationSound(this)
        }

        return START_STICKY
    }

    private fun playNotificationSound(context: Context?) {
        Log.d("TTT", "MyReceiver -> playNotificationSound ")

        try {
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val mediaPlayer = MediaPlayer.create(context, notification)
            mediaPlayer.setOnCompletionListener {
                mediaPlayer.release()
            }
            mediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(notificationChannelId, "DEMO", NotificationManager.IMPORTANCE_DEFAULT)
            channel.setSound(null, null)

            val service = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            service.createNotificationChannel(channel)
        }
    }

    private fun startMyService() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification: Notification = NotificationCompat.Builder(this, notificationChannelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText("Hello bro!")
            .setContentTitle("What's up?")
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .build()

        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TTT", "MyService -> onDestroy ")
        unregisterReceiver(myReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
