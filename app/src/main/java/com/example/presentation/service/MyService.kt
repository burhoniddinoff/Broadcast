package com.example.presentation.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.broadcast.R
import com.example.presentation.activity.MainActivity
import com.example.presentation.broadcast.MyReceiver
import com.example.presentation.myLog
import java.util.Locale

class MyService : Service(), TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    private val batteryChangeReceiver = MyReceiver()
    private val channel = "DEMO"


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        tts = TextToSpeech(this, this)

        val intentFilter = IntentFilter()

        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            registerReceiver(batteryChangeReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)

        tts = TextToSpeech(this, this)
        batteryChangeReceiver.listener = {
            speakOut(it)
            "$it text speakOut()".myLog()
        }

        createChanel()
        startMyService()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.JAPANESE)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                Log.e("TTS", "Язык не поддерживается")
        } else Log.e("TTS", "Инициализация не удалась")
    }

    private fun speakOut(text: String) = tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        unregisterReceiver(batteryChangeReceiver)
        super.onDestroy()
    }

    @SuppressLint("ForegroundServiceType")
    private fun startMyService() {

        val notificationIntent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification: Notification = NotificationCompat.Builder(this, channel)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Hello bro ?")
            .setContentText("What's up!")
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .build()

        startForeground(1, notification)

    }

    private fun createChanel() {
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel("DEMO", channel, NotificationManager.IMPORTANCE_DEFAULT)
            channel.setSound(null, null)

            val service = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            service.createNotificationChannel(channel)
        }
    }

}