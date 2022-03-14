package com.alish.geekbank.data.local.preferences

enum class Localization(
    val option1: String,
    val option2: String,
    val languageCode: String,
    val language: String,
) {

    RUSSIAN(
        "ru",
        "RU",
        "ru-Ru",
        "ru"
    ),
    ENGLISH(
        "en",
        "US",
        "en-US",
        "en"
    )
}