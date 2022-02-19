package com.nestor.superheromvvm.data.repository.character

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nestor.superheromvvm.data.model.CharacterDataWrapper
import com.nestor.superheromvvm.data.remote.CharacterService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val service: CharacterService) :
    CharacterRepository {
    private suspend fun fetchCharacters(key: CharacterPaginationKey): Response<CharacterDataWrapper> =
        service.getCharacters(limit = key.limit, offset = key.offset)

    override fun getCharacters(): Flow<PagingData<CharacterDataWrapper.CharacterDataContainer.Character>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_CHARACTER_LIMIT),
            pagingSourceFactory = { CharactersPagingSource(networkCall = { fetchCharacters(it) }) }
        ).flow
    }
}