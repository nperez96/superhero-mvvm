package com.nestor.superheromvvm.data.repository.character

import com.nestor.superheromvvm.data.model.*
import com.nestor.superheromvvm.data.remote.CharacterService
import com.nestor.superheromvvm.util.convertResponse
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "CharacterRepositoryImpl"

/**
 * FIXME: Should fetch character stories from CharacterService.getCharacterStories providing the characterId and the limit and offset from PaginationKey
 */
class CharacterRepositoryImpl @Inject constructor(private val service: CharacterService) :
    CharacterRepository {
    override suspend fun getCharacters(key: PaginationKey): Response<CharacterDataWrapper> =
        service.getCharacters(limit = key.limit, offset = key.offset)

    override suspend fun getCharacter(characterId: Int): Response<CharacterDataWrapper.CharacterDataContainer.Character> {
        return service.getCharacterById(characterId).convertResponse { it.data.results[0] }
    }

    override suspend fun getCharacterEvents(
        characterId: Int,
        key: PaginationKey
    ): Response<CharacterEventsDataWrapper> {
        return service.getCharacterEvents(
            characterId = characterId,
            limit = key.limit,
            offset = key.offset
        )
    }

    override suspend fun getCharacterComics(
        characterId: Int,
        key: PaginationKey
    ): Response<CharacterComicsDataWrapper> {
        return service.getCharacterComics(
            characterId = characterId,
            limit = key.limit,
            offset = key.offset
        )
    }

    override suspend fun getCharacterSeries(
        characterId: Int,
        key: PaginationKey
    ): Response<CharacterSeriesDataWrapper> {
        return service.getCharacterSeries(
            characterId = characterId,
            limit = key.limit,
            offset = key.offset
        )
    }
}