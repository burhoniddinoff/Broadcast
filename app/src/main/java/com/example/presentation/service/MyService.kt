package com.example.presentation.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.broadcast.R
import com.example.presentation.activity.MainActivity
import com.example.presentation.broadcast.MyReceiver
import java.util.Locale

class MyService : Service(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private val myReceiver = MyReceiver()
    private val notificationChannelId = "DEMO"

    override fun onCreate() {
        super.onCreate()

        createChannel()
        startMyService()

        val intentFilter = IntentFilter().apply {
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        }
        registerReceiver(myReceiver, intentFilter)

        myReceiver.bluetoothClick = {
            speakOut(it)
        }

        tts = TextToSpeech(this, this)
    }

//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        Log.d("TTT", "MyService -> onStartCommand ")
//
//
//        return START_STICKY
//    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
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

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Язык не поддерживается")
            } else {

            }
        } else {
            Log.e("TTS", "Инициализация не удалась")
        }

    }
}
