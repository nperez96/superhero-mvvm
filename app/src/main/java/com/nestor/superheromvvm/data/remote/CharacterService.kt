package com.nestor.superheromvvm.data.remote

import com.nestor.superheromvvm.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {
    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<CharacterDataWrapper>

    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id") characterId: Int,
    ): Response<CharacterDataWrapper>

    @GET("characters/{id}/series")
    suspend fun getCharacterSeries(
        @Path("id") characterId: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<CharacterSeriesDataWrapper>

    @GET("characters/{id}/comics")
    suspend fun getCharacterComics(
        @Path("id") characterId: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<CharacterComicsDataWrapper>

    @GET("characters/{id}/stories")
    suspend fun getCharacterStories(
        @Path("id") characterId: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<CharacterStoriesDataWrapper>

    @GET("characters/{id}/events")
    suspend fun getCharacterEvents(
        @Path("id") characterId: Int,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<CharacterEventsDataWrapper>
}