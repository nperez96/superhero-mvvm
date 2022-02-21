package com.nestor.superheromvvm.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nestor.superheromvvm.BuildConfig
import com.nestor.superheromvvm.data.remote.CharacterService
import com.nestor.superheromvvm.data.remote.interceptors.AuthorizationInterceptor
import com.nestor.superheromvvm.data.remote.interceptors.NetworkThrottlerInterceptor
import com.nestor.superheromvvm.data.repository.date.DateRepository
import com.nestor.superheromvvm.data.repository.date.DateRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()


    @Singleton
    @Provides
    fun providesDateRepository(): DateRepository = DateRepositoryImpl()

    @Singleton
    @Provides
    fun providesClient(dateRepository: DateRepository): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationInterceptor(dateRepository))
        .addInterceptor(NetworkThrottlerInterceptor(250))
        /**
         * max amount of concurrent api calls, this to prevent 429
         */
        .dispatcher(Dispatcher().apply { maxRequests = 2 })
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson,
        client: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideHeroService(retrofit: Retrofit): CharacterService =
        retrofit.create(CharacterService::class.java);
}