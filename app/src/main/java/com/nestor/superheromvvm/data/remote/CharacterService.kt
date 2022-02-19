package com.nestor.superheromvvm.data.remote

import com.nestor.superheromvvm.data.model.CharacterDataWrapper
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

    @GET("character/:id")
    fun getCharacterById(
        @Path("id") characterId: Int,
    ): Response<CharacterDataWrapper>
}