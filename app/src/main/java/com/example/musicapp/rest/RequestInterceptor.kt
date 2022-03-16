package com.example.musicapp.rest

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor :Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            addHeader("HEADER1", "FirstHeader")
            addHeader("HEADER2", "SecondHeader")
            addHeader("HEADER3", "ThirdHeader")
        }.build()

        // Log.d("HEADERS", nRequest.headers["HEADER1"].toString())

        return chain.proceed(request)
    }

}