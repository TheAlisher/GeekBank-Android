package com.alish.geekbank.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import com.alish.geekbank.common.constants.Constants
import javax.inject.Singleton

@Singleton
class PreferencesHelper(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)


    var pinCode: String?
        get() = preferences.getString("code", "")
        set(pasCode) = preferences.edit().putString("code", pasCode).apply()

    fun putBoolean(key: String, value: Boolean) {
        var editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun putOnBoardBoolean() {
        var editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean("OnBoard", true).apply()
        editor.apply()
    }

    fun getOnBoardBoolean(): Boolean {
        return preferences.getBoolean("OnBoard", false)
    }

    fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun putString(key: String, value: String) {
        var editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun onSaveOnPinCode() {
        preferences.edit().putBoolean("onPinCode", true).apply()
    }

    fun isShownPinCode(): Boolean {
        return preferences.getBoolean("onPinCode", false)
    }

    fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    fun isShown(): Boolean {
        return preferences.getBoolean("show", false)
    }

    private fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun getLanguage() = preferences.getString(Constants.LANGUAGE, "ru")

    fun getLanguageCode() = preferences.getString(Constants.LANGUAGE_CODE, "ru-RU")

    fun setLocale(localization: Localization) {
        preferences.edit().apply {
            putString(Constants.LANGUAGE, localization.language).apply()
            putString(Constants.LANGUAGE_CODE, localization.languageCode).apply()
        }
    }

    companion object {
        private const val DARK_STATUS = "io.github.manuelernesto.DARK_STATUS"
    }

    var darkMode = preferences.getInt(DARK_STATUS, 0)
        set(value) = preferences.edit().putInt(DARK_STATUS, value).apply()


}