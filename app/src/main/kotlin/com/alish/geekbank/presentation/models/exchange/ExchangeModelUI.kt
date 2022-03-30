package com.alish.geekbank.presentation.models.exchange

import com.alish.geekbank.domain.models.ExchangeModel

data class ExchangeModelUI(
    val base_code: String,
    val conversion_rates: Map<String, Double>,
    val documentation: String,
    val result: String,
    val terms_of_use: String,
    val time_last_update_unix: Int,
    val time_last_update_utc: String,
    val time_next_update_unix: Int,
    val time_next_update_utc: String
)

fun ExchangeModel.toUI() = ExchangeModelUI(
    base_code,
    conversion_rates,
    documentation,
    result,
    terms_of_use,
    time_last_update_unix,
    time_last_update_utc,
    time_next_update_unix,
    time_next_update_utc
)