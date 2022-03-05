package com.alish.geekbank.data.remote.apiservices


import com.alish.geekbank.data.remote.dtos.NewsModelDto
import com.alish.geekbank.data.remote.dtos.NewsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("/v2/everything?apiKey=4633cbf21ce74b59850d9c56f1cbc60f")
    suspend fun fetchNews(
        @Query("q") query: String?
    ): NewsResponseDto<NewsModelDto>

}