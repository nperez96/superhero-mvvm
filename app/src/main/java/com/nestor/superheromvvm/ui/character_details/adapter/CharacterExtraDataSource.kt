package com.nestor.superheromvvm.ui.character_details.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nestor.superheromvvm.data.model.CharacterExtraData
import com.nestor.superheromvvm.data.repository.character.DEFAULT_CHARACTER_EXTRA_DATA_LIMIT
import com.nestor.superheromvvm.data.repository.character.DEFAULT_CHARACTER_LIMIT
import com.nestor.superheromvvm.data.repository.character.PaginationKey
import com.nestor.superheromvvm.util.nextKey
import com.nestor.superheromvvm.util.previousKey
import retrofit2.Response

/**
 * This class will handle the refresh logic.
 */
class CharacterExtraDataSource(private val loadData: suspend (key: PaginationKey) -> Response<List<CharacterExtraData>>) :
    PagingSource<PaginationKey, CharacterExtraData>() {

    /**
     * Get the key to refresh the current state, if the item is at the bottom it will get the prev key,
     * if the item is at the top will get the next key and if the item is the first item will return null
     * cause theres no previous key
     */
    override fun getRefreshKey(state: PagingState<PaginationKey, CharacterExtraData>): PaginationKey? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.nextKey() ?: anchorPage?.nextKey?.previousKey()
        }
    }

    /**
     * Will load the next page extracting the chars from the response, and send the key for the next/previous request
     */
    override suspend fun load(params: LoadParams<PaginationKey>): LoadResult<PaginationKey, CharacterExtraData> {
        val currentKey =
            params.key ?: PaginationKey(limit = DEFAULT_CHARACTER_EXTRA_DATA_LIMIT, offset = 0)
        return try {
            val response = loadData.invoke(currentKey)
            val data = response.body()!!
            val nextKey = if (data.isEmpty()) null else currentKey.nextKey()
            LoadResult.Page(
                data = data,
                prevKey = currentKey.previousKey(),
                nextKey = nextKey,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}