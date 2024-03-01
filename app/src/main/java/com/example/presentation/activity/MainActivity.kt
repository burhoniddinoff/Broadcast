package com.example.presentation.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.broadcast.R
import com.example.broadcast.databinding.ActivityMainBinding
import com.example.presentation.MySharedPreference
import com.example.presentation.service.MyService

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val shared = MySharedPreference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startService(Intent(this, MyService::class.java))

        initSwipe()
    }


    private fun initSwipe() {
        binding.switch1.isChecked = shared.getSwipe("switch1")
        binding.switch1.setOnCheckedChangeListener { _, check ->
            shared.setSwipe("switch1", check)
        }
        Log.d("TTT", "${binding.switch1.isChecked}")

        binding.switch3.isChecked = shared.getSwipe("switch3")
        binding.switch3.setOnCheckedChangeListener { _, check ->
            shared.setSwipe("switch3", check)
        }
        Log.d("TTT", "${binding.switch3.isChecked}")
    }
}