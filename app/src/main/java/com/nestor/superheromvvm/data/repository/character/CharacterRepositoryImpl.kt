package com.nestor.superheromvvm.data.repository.character

import com.nestor.superheromvvm.data.model.CharacterDataWrapper
import com.nestor.superheromvvm.data.remote.CharacterService
import retrofit2.Response
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val service: CharacterService) :
    CharacterRepository {
    override suspend fun getCharacters(key: CharacterPaginationKey): Response<CharacterDataWrapper> =
        service.getCharacters(limit = key.limit, offset = key.offset)
}