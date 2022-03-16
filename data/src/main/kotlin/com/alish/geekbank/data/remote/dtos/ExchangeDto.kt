package com.alish.geekbank.data.remote.dtos

import com.alish.geekbank.domain.models.ExchangeModel
import com.google.gson.annotations.SerializedName

data class ExchangeDto(
    @SerializedName("base_code")
    val base_code: String,
    @SerializedName("conversion_rates")
    val conversion_rates: Map<String, Double>,
    @SerializedName("documentation")
    val documentation: String,
    @SerializedName("result")
    val result: String,
    @SerializedName("terms_of_use")
    val terms_of_use: String,
    @SerializedName("time_last_update_unix")
    val time_last_update_unix: Int,
    @SerializedName("time_last_update_utc")
    val time_last_update_utc: String,
    @SerializedName("time_next_update_unix")
    val time_next_update_unix: Int,
    @SerializedName("time_next_update_utc")
    val time_next_update_utc: String,
)

fun ExchangeDto.toDomain() = ExchangeModel(
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