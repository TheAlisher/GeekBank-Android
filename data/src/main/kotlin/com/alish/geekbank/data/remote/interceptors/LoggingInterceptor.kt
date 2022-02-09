package com.alish.geekbank.data.remote.interceptors

import okhttp3.logging.HttpLoggingInterceptor

class LoggingInterceptor {

    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}