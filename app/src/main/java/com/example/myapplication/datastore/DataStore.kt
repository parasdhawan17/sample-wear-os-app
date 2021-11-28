package com.example.myapplication.datastore

import android.content.Context
import com.example.myapplication.R

class DataStore(private val context : Context) {
    private val sharedPref = context.getSharedPreferences(
        context.getString(R.string.share_pref_key), Context.MODE_PRIVATE)

    fun saveInt(key : String, value : Int){
        with (sharedPref.edit()) {
            putInt(key,value)
            apply()
        }
    }

    fun getInt(key : String) : Int{
        return sharedPref.getInt(key,-1)
    }
}