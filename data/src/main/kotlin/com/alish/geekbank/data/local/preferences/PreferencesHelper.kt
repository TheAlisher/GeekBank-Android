package com.alish.geekbank.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import com.alish.geekbank.common.constants.Constants
import javax.inject.Singleton

@Singleton
class PreferencesHelper(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)


    var userID: String?
        get() = preferences.getString("userID", "")
        set(valueID) = preferences.edit().putString("userID", valueID).apply()

    var pinCode: String?
        get() = preferences.getString("code", "")
        set(pasCode) = preferences.edit().putString("code", pasCode).apply()

    var isChange: Boolean
        get() = preferences.getBoolean("change", false)
        set(value) = preferences.edit().putBoolean("change", value).apply()

    fun putBoolean(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun putOnBoardBoolean() {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean("OnBoard", true).apply()
        editor.apply()
    }

    fun getOnBoardBoolean(): Boolean {
        return preferences.getBoolean("OnBoard", false)
    }


    fun putString(key: String, value: String) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    fun getLanguage() = preferences.getString(Constants.LANGUAGE, "ru")

    fun getLanguageCode() = preferences.getString(Constants.LANGUAGE_CODE, "ru-RU")

    fun setLocale(localization: Localization) {
        preferences.edit().apply {
            putString(Constants.LANGUAGE, localization.language).apply()
            putString(Constants.LANGUAGE_CODE, localization.languageCode).apply()
        }
    }
}