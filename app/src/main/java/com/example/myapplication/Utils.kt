package com.example.myapplication

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun convertDateToFormat(date: Date, format: String): String {
    val spf = SimpleDateFormat(format)
    return spf.format(date)
}