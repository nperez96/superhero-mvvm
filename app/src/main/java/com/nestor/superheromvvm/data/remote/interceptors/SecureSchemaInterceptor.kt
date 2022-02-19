package com.nestor.superheromvvm.data.remote.interceptors

import com.nestor.superheromvvm.BuildConfig
import com.nestor.superheromvvm.data.repository.date.DateRepository
import com.nestor.superheromvvm.util.toMd5
import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest
import java.util.*

/**
 * changes from http to https
 * more info: https://developer.marvel.com/documentation/authorization
 */
class SecureSchemaInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url()
            .newBuilder()
            .scheme("https")
        return chain.proceed(
            chain.request()
                .newBuilder()
                .url(url.build())
                .build()
        )
    }
}