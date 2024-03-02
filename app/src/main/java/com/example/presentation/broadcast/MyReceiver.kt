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
import com.example.presentation.myLog


class MyReceiver : BroadcastReceiver() {
    var listener: ((String) -> Unit)? = null
    private val myShar = MySharedPreference

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {

        when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {
            BluetoothAdapter.STATE_ON -> {
                if (myShar.getSwipe("swipe1")) listener?.invoke("ブルートゥースがオン")
                "${myShar.getSwipe("swipe1")} SWIPE 1".myLog()
            }

            BluetoothAdapter.STATE_OFF -> {
                if (myShar.getSwipe("swipe1")) listener?.invoke("ブルートゥースオフ")
                "${myShar.getSwipe("swipe1")} SWIPE 1".myLog()
            }
        }

        when (intent.action) {

            Intent.ACTION_BATTERY_CHANGED -> if (myShar.getSwipe("swipe2")) {
                listener?.invoke("${intent.extras?.getInt(BatteryManager.EXTRA_LEVEL)} 比率")
                "${myShar.getSwipe("swipe2")} SWIPE 2".myLog()
            }

            ConnectivityManager.CONNECTIVITY_ACTION -> {
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                val isConnected = activeNetworkInfo?.isConnectedOrConnecting == true

                if (myShar.getSwipe("swipe3")) {
                    if (isConnected) listener?.invoke("インターネットはオンになっています")
                    else listener?.invoke("インターネットがオフになっています")

                    "${myShar.getSwipe("swipe3")} SWIPE 3".myLog()
                }
            }

            TelephonyManager.ACTION_PHONE_STATE_CHANGED -> {
                val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
                if (state == TelephonyManager.EXTRA_STATE_RINGING && myShar.getSwipe("swipe4")) {
                    val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                    listener?.invoke("番号があなたに電話をかけています: $incomingNumber")

                    "${myShar.getSwipe("swipe4")} SWIPE 4".myLog()
                }
            }

            Intent.ACTION_SCREEN_OFF -> {
                if (myShar.getSwipe("swipe5")) listener?.invoke("画面上")
                "${myShar.getSwipe("swipe5")} SWIPE 5".myLog()
            }

            Intent.ACTION_SCREEN_ON -> if (myShar.getSwipe("swipe5")) {
                listener?.invoke("画面オフ")
                "${myShar.getSwipe("swipe5")} SWIPE 5".myLog()
            }

        }
    }
}