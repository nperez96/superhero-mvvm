package com.nestor.superheromvvm.data.remote.interceptors

import android.os.SystemClock
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Network throttler to prevent api to answer with 429 too many requests.
 */
class NetworkThrottlerInterceptor constructor(private val throttlerForMs: Long) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        SystemClock.sleep(throttlerForMs)
        return chain.proceed(chain.request());
    }
}