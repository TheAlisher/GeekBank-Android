package com.alish.geekbank.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import com.alish.geekbank.common.constants.Constants
import javax.inject.Singleton

@Singleton
class LanguagePreferencesHelper(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun getLanguage() = preferences.getString(Constants.LANGUAGE, "ru")

    fun getLanguageCode() = preferences.getString(Constants.LANGUAGE_CODE, "ru-RU")

    fun setLocale(localization: Localization) {
        preferences.edit().apply {
            putString(Constants.LANGUAGE, localization.language).apply()
            putString(Constants.LANGUAGE_CODE, localization.languageCode).apply()
        }
    }
}