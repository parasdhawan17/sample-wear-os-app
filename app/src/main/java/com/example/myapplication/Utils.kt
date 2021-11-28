package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.jwang123.flagkit.FlagKit
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun convertDateToFormat(date: Date, format: String): String {
    val spf = SimpleDateFormat(format)
    return spf.format(date)
}

fun String.twoDigit() : String{
    if(this.length <= 2) return this
    return this.substring(0..1)
}

fun setCountryFlag(context : Context,imageView : ImageView,countryCode : String){
    try {
        val drawable = FlagKit.drawableWithFlag(
            context,
            countryCode.twoDigit().lowercase()
        )
        imageView.setImageDrawable(drawable)
    }catch (e : Exception){
        e.printStackTrace()
    }
}