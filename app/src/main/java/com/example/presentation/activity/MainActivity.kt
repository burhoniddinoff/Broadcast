package com.example.presentation.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.broadcast.databinding.ActivityMainBinding
import com.example.presentation.MySharedPreference
import com.example.presentation.service.MyService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val myShar = MySharedPreference

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSwipe()

        val intent = Intent(this, MyService::class.java)
        startService(intent)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 123)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.FOREGROUND_SERVICE), 123)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH), 123)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH_ADMIN), 123)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_NETWORK_STATE), 123)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), 123)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CALL_LOG), 123)

    }

    private fun initSwipe() {
        binding.apply {
            switch1.isChecked = myShar.getSwipe("swipe1")
            switch1.setOnCheckedChangeListener { _, isChecked ->
                myShar.setSwipe("swipe1", isChecked)
            }

            switch2.isChecked = myShar.getSwipe("swipe2")
            switch2.setOnCheckedChangeListener { _, isChecked ->
                myShar.setSwipe("swipe2", isChecked)
            }

            switch3.isChecked = myShar.getSwipe("swipe3")
            switch3.setOnCheckedChangeListener { _, isChecked ->
                myShar.setSwipe("swipe3", isChecked)
            }

            switch4.isChecked = myShar.getSwipe("swipe4")
            switch4.setOnCheckedChangeListener { _, isChecked ->
                myShar.setSwipe("swipe4", isChecked)
            }

            switch5.isChecked = myShar.getSwipe("swipe5")
            switch5.setOnCheckedChangeListener { _, isChecked ->
                myShar.setSwipe("swipe5", isChecked)
            }
        }
    }
}