package com.alish.geekbank.data.remote.apiservices

import com.alish.geekbank.data.remote.dtos.ExchangeDto
import retrofit2.http.GET

interface ExchangeApiService {
    @GET("/v6/8238fe64f361f7ae1ec666f7/latest/KGS")
    suspend fun fetchExchange(): ExchangeDto
}