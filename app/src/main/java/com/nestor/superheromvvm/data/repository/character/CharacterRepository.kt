package com.nestor.superheromvvm.data.repository.character

import com.nestor.superheromvvm.data.model.CharacterDataWrapper
import retrofit2.Response

interface CharacterRepository {
    suspend fun getCharacters(key: CharacterPaginationKey): Response<CharacterDataWrapper>
}
