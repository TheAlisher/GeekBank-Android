package com.alish.geekbank.data.remote.apiservices


import com.alish.geekbank.data.remote.dtos.NewsModelDto
import com.alish.geekbank.data.remote.dtos.NewsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface   NewsApiService {

    @GET("/v2/everything?apiKey=c08ab6a617d0486d8677c0ba7bcb18bc")
    suspend fun fetchNews(
        @Query("q") query: String?
    ): NewsResponseDto<NewsModelDto>

}