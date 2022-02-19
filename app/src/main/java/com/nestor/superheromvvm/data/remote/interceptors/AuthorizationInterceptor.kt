package com.nestor.superheromvvm.data.remote.interceptors

import com.nestor.superheromvvm.BuildConfig
import com.nestor.superheromvvm.data.repository.date.DateRepository
import com.nestor.superheromvvm.util.toMd5
import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest
import java.util.*

/**
 * Append the required parameters for marvel api: hash, ts, apikey
 * more info: https://developer.marvel.com/documentation/authorization
 */
class AuthorizationInterceptor(private val dateRepository: DateRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val publicKey = BuildConfig.PUBLIC_API_KEY
        val privateKey = BuildConfig.PIVATE_API_KEY
        val timestamp = dateRepository.getCurrentTimestamp()
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("apikey", publicKey)
            .addQueryParameter("ts", timestamp.toString())
            .addQueryParameter("hash", "$timestamp$privateKey$publicKey".toMd5())
        return chain.proceed(
            chain.request()
                .newBuilder()
                .url(url.build())
                .build()
        )
    }
}