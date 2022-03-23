package com.alish.geekbank.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Singleton

@Singleton
class PreferencesHelper(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)


    var pinCode: String?
        get() = preferences.getString("code", "")
        set(pasCode) = preferences.edit().putString("code", pasCode).apply()

    fun putBoolean(key: String,value:Boolean){
        var editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean(key,value)
        editor.apply()
    }
    fun getBoolean(key: String):Boolean{
        return preferences.getBoolean(key,false)
    }

    fun isShown(): Boolean {
        return preferences.getBoolean("show", false)
    }
}