package com.nestor.superheromvvm.util

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream
import com.bumptech.glide.load.model.GlideUrl
import com.nestor.superheromvvm.data.remote.interceptors.AuthorizationInterceptor
import com.nestor.superheromvvm.data.remote.interceptors.NetworkThrottlerInterceptor
import com.nestor.superheromvvm.data.remote.interceptors.SecureSchemaInterceptor
import com.nestor.superheromvvm.data.repository.date.DateRepositoryImpl
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Singleton
@GlideModule
@Excludes(OkHttpLibraryGlideModule::class)
class MyGlideModule constructor() :
    AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        //  todo: change this to use hilt injection
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor(DateRepositoryImpl()))
            .addInterceptor(SecureSchemaInterceptor())
            .addInterceptor(NetworkThrottlerInterceptor(100))
            /**
             * max amount of concurrent api calls, this to prevent 429
             */
            .dispatcher(Dispatcher().apply { maxRequests = 3 })
            .build()
        glide.registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(client)
        )

    }
}