package com.alish.geekbank.data.remote.apiservices

import com.alish.geekbank.data.remote.dtos.CardsPagingResponseDto
import com.alish.geekbank.data.remote.dtos.FooDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FooApiService {

    @GET("/api/foo")
    suspend fun fetchCards(): FooDto

    @GET("/api/foo")
    suspend fun fetchCardsPaging(
        @Query("page") page: Int,
    ): Response<CardsPagingResponseDto<FooDto>>
}