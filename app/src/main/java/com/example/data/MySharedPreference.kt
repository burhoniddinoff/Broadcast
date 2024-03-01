package com.example.data

import android.content.Context
import android.content.SharedPreferences

object MySharedPreference {

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("demo", Context.MODE_PRIVATE)
    }

    fun setSwipe(s: String, b: Boolean) {
        sharedPreferences.edit().putBoolean(s, b).apply()
    }

    fun getSwipe(s: String): Boolean {
        return sharedPreferences.getBoolean(s, true)
    }

}