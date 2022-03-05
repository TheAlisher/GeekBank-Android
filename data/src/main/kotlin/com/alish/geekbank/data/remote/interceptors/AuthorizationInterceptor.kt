package com.alish.geekbank.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader("X-Api-Key", "4633cbf21ce74b59850d9c56f1cbc60f")
            .build()
        return chain.proceed(request)
    }
}