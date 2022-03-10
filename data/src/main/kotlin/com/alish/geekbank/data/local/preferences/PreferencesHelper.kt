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
        set(value) = preferences.edit().putString("code", value).apply()

    fun putBoolean(key: String,value:Boolean){
        var editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean(key,value)
        editor.apply()
    }
    fun getBoolean(key: String):Boolean{
        return preferences.getBoolean(key,false)
    }

    fun putString(key: String,value:String){
        var editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(key,value)
        editor.apply()
    }
    fun getString(key: String):String?{
        return preferences.getString(key,null)
    }

    fun onSavePinCode() {
        preferences.edit().putBoolean("show", true).apply()
    }

    fun isShown(): Boolean {
        return preferences.getBoolean("show", false)
    }

    private fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }
}