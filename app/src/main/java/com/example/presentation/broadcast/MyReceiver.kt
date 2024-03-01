package com.example.presentation.broadcast

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.data.MySharedPreference

class MyReceiver : BroadcastReceiver() {

    var bluetoothClick: ((String) -> Unit)? = null
    private val shared = MySharedPreference

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("TTT", "MyReceiver -> onReceive ")

        when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {

            BluetoothAdapter.STATE_ON -> {
                if (shared.getSwipe("swipe3")) {
                    bluetoothClick?.invoke("Bluetooth turned on")
                }
            }

            BluetoothAdapter.STATE_OFF -> {
                if (shared.getSwipe("swipe3")) {
                    bluetoothClick?.invoke("Bluetooth turned off")
                }
            }
        }

    }
}