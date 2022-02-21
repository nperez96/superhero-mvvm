package com.nestor.superheromvvm.data.repository.character

import com.nestor.superheromvvm.data.model.*
import retrofit2.Response

interface CharacterRepository {
    suspend fun getCharacters(key: PaginationKey): Response<CharacterDataWrapper>
    suspend fun getCharacter(characterId: Int): Response<CharacterDataWrapper.CharacterDataContainer.Character>

    suspend fun getCharacterStories(
        characterId: Int,
        key: PaginationKey
    ): Response<CharacterStoriesDataWrapper>

    suspend fun getCharacterEvents(
        characterId: Int,
        key: PaginationKey
    ): Response<CharacterEventsDataWrapper>

    suspend fun getCharacterComics(
        characterId: Int,
        key: PaginationKey
    ): Response<CharacterComicsDataWrapper>

    suspend fun getCharacterSeries(
        characterId: Int,
        key: PaginationKey
    ): Response<CharacterSeriesDataWrapper>
}
