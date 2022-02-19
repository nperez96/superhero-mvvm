package com.nestor.superheromvvm.data.repository.character

import androidx.paging.PagingData
import com.nestor.superheromvvm.data.model.CharacterDataWrapper
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(): Flow<PagingData<CharacterDataWrapper.CharacterDataContainer.Character>>
}
