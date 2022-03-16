package com.alish.geekbank.data.remote

import com.alish.geekbank.data.remote.apiservices.ExchangeApiService
import com.alish.geekbank.data.remote.apiservices.NewsApiService
import com.alish.geekbank.data.remote.interceptors.AuthorizationInterceptor
import com.alish.geekbank.data.remote.interceptors.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    private val okHttpClient: OkHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(LoggingInterceptor().provideLoggingInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(AuthorizationInterceptor())
        .build()


    private val provideRetrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun provideNewsApiService(): NewsApiService = provideRetrofit
        .create(NewsApiService::class.java)

    fun provideExchangeApiService(): ExchangeApiService = provideRetrofit
        .create(ExchangeApiService::class.java)
}