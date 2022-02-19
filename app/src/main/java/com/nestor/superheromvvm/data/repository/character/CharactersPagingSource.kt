package com.nestor.superheromvvm.data.repository.character

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nestor.superheromvvm.data.model.CharacterDataWrapper
import com.nestor.superheromvvm.util.nextKey
import com.nestor.superheromvvm.util.previousKey
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception
import java.net.UnknownHostException
import javax.inject.Inject

private const val TAG = "CharactersPagingSource"

/**
 * This class will handle the refresh logic.
 */
class CharactersPagingSource(private val repository: CharacterRepository) :
    PagingSource<CharacterPaginationKey, CharacterDataWrapper.CharacterDataContainer.Character>() {

    /**
     * Get the key to refresh the current state, if the item is at the bottom it will get the prev key,
     * if the item is at the top will get the next key and if the item is the first item will return null
     * cause theres no previous key
     */
    override fun getRefreshKey(state: PagingState<CharacterPaginationKey, CharacterDataWrapper.CharacterDataContainer.Character>): CharacterPaginationKey? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.nextKey() ?: anchorPage?.nextKey?.previousKey()
        }
    }

    /**
     * Will load the next page extracting the chars from the response, and send the key for the next/previous request
     */
    override suspend fun load(params: LoadParams<CharacterPaginationKey>): LoadResult<CharacterPaginationKey, CharacterDataWrapper.CharacterDataContainer.Character> {
        val currentKey =
            params.key ?: CharacterPaginationKey(limit = DEFAULT_CHARACTER_LIMIT, offset = 0)
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