package com.alish.geekbank.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader("X-Api-Key", "c08ab6a617d0486d8677c0ba7bcb18bc")
            .build()
        return chain.proceed(request)
    }
}