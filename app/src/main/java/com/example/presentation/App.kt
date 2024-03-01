package com.example.presentation

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MySharedPreference.init(this)
    }

}