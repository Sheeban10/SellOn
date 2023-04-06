package com.example.sellon.utilities

import android.content.Context
import android.content.SharedPreferences

class SharedPref(contex: Context) {

    var sharedPref: SharedPreferences
    init {
            sharedPref =contex.getSharedPreferences(Constants.SharedPrefName,0)
    }
    fun setString(key: String, value:String){
        sharedPref.edit().putString(key,value).apply()
    }

    fun getString(key: String): String? {
        return sharedPref.getString(key,"")
    }

}

