package com.example.broadcast

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.util.Log

class BatteryReceiver : BroadcastReceiver() {



    override fun onReceive(context: Context, intent: Intent) {

        when (intent.action) {

            WifiManager.WIFI_STATE_CHANGED_ACTION -> {}

        }

        if (intent.action == Intent.ACTION_BATTERY_CHANGED) {

            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val batteryLevel = level * 100 / scale.toFloat()

            Log.d("TTT", "Battery Level: $batteryLevel%")

        }

    }

}