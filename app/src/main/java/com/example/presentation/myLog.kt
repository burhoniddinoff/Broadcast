package com.example.presentation

import android.content.Context
import android.util.Log
import android.widget.Toast

fun String.myLog() = Log.d("TTT", this)
fun String.onlyLetters() = all { it.isLetter() }
fun String.myShortToast(context: Context){
    Toast.makeText(context,this , Toast.LENGTH_SHORT).show()
}

