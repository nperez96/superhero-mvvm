package com.nestor.superheromvvm.ui.character_list.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nestor.superheromvvm.data.model.CharacterDataWrapper
import com.nestor.superheromvvm.data.repository.character.CharacterRepository
import com.nestor.superheromvvm.data.repository.character.DEFAULT_CHARACTER_LIMIT
import com.nestor.superheromvvm.data.repository.character.PaginationKey
import com.nestor.superheromvvm.util.nextKey
import com.nestor.superheromvvm.util.previousKey
import java.lang.Exception

/**
 * This class will handle the refresh logic.
 */
class CharactersPagingSource(private val repository: CharacterRepository) :
    PagingSource<PaginationKey, CharacterDataWrapper.CharacterDataContainer.Character>() {

    /**
     * Get the key to refresh the current state, if the item is at the bottom it will get the prev key,
     * if the item is at the top will get the next key and if the item is the first item will return null
     * cause theres no previous key
     */
    override fun getRefreshKey(state: PagingState<PaginationKey, CharacterDataWrapper.CharacterDataContainer.Character>): PaginationKey? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.nextKey() ?: anchorPage?.nextKey?.previousKey()
        }
    }

    /**
     * Will load the next page extracting the chars from the response, and send the key for the next/previous request
     */
    override suspend fun load(params: LoadParams<PaginationKey>): LoadResult<PaginationKey, CharacterDataWrapper.CharacterDataContainer.Character> {
        val currentKey =
            params.key ?: PaginationKey(limit = DEFAULT_CHARACTER_LIMIT, offset = 0)
        return try {
            val response = repository.getCharacters(currentKey)
            val characters = response.body()!!.data.results
            val nextKey = if (characters.isEmpty()) null else currentKey.nextKey()
            LoadResult.Page(
                data = characters,
                prevKey = currentKey.previousKey(),
                nextKey = nextKey,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}