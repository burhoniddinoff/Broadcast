package com.example.app

import android.app.Application
import com.example.data.MySharedPreference

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MySharedPreference.init(this)
    }

}