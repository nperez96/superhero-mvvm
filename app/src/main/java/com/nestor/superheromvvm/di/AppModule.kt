package com.nestor.superheromvvm.di

import android.content.Context
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nestor.superheromvvm.BuildConfig
import com.nestor.superheromvvm.data.remote.CharacterService
import com.nestor.superheromvvm.data.remote.interceptors.AuthorizationInterceptor
import com.nestor.superheromvvm.data.repository.date.DateRepository
import com.nestor.superheromvvm.data.repository.date.DateRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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