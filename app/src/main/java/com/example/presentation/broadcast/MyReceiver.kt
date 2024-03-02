package com.example.presentation.broadcast

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.BatteryManager
import android.telephony.TelephonyManager
import com.example.presentation.MySharedPreference


class MyReceiver : BroadcastReceiver() {
    var listener: ((String) -> Unit)? = null
    private val myShar = MySharedPreference

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {

        when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {
            BluetoothAdapter.STATE_ON -> if (myShar.getSwipe("swipe1")) listener?.invoke("Bluetooth включон")
            BluetoothAdapter.STATE_OFF -> if (myShar.getSwipe("swipe1")) listener?.invoke("Bluetooth выключон")
        }

        when (intent.action) {

            Intent.ACTION_BATTERY_CHANGED -> if (myShar.getSwipe("swipe2")) listener?.invoke("${intent.extras?.getInt(BatteryManager.EXTRA_LEVEL)} процентов")

            ConnectivityManager.CONNECTIVITY_ACTION -> {
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                val isConnected = activeNetworkInfo?.isConnectedOrConnecting == true

                if (myShar.getSwipe("swipe3")) {
                    if (isConnected) listener?.invoke("Интернет подключен")
                    else listener?.invoke("Интернет отключен")
                }
            }

            TelephonyManager.ACTION_PHONE_STATE_CHANGED -> {
                val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
                if (state == TelephonyManager.EXTRA_STATE_RINGING && myShar.getSwipe("swipe4")) {
                    val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                    listener?.invoke("Вам звонит номер: $incomingNumber")
                }
            }

            Intent.ACTION_SCREEN_OFF -> if (myShar.getSwipe("swipe5")) listener?.invoke("Экран выключен")
            Intent.ACTION_SCREEN_ON -> if (myShar.getSwipe("swipe5")) listener?.invoke("Экран включен")

        }
    }
}