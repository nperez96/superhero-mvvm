package com.nestor.superheromvvm.ui.character_details.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nestor.superheromvvm.data.converters.toCharacterExtraData
import com.nestor.superheromvvm.data.model.CharacterDataWrapper
import com.nestor.superheromvvm.data.model.CharacterExtraData
import com.nestor.superheromvvm.data.repository.character.CharacterRepository
import com.nestor.superheromvvm.data.repository.character.DEFAULT_CHARACTER_EXTRA_DATA_LIMIT
import com.nestor.superheromvvm.data.repository.character.PaginationKey
import com.nestor.superheromvvm.ui.character_details.adapter.CharacterExtraDataSource
import com.nestor.superheromvvm.util.convertResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(private val repository: CharacterRepository) :
    ViewModel() {
    private val _characterId = MutableSharedFlow<Int>(replay = 1)
    val comics: Flow<PagingData<CharacterExtraData>> =
        _characterId.flatMapLatest { generatePagerForComics(it).flow.cachedIn(viewModelScope) }

    val series: Flow<PagingData<CharacterExtraData>> =
        _characterId.flatMapLatest { generatePagerForSeries(it).flow.cachedIn(viewModelScope) }

    val stories: Flow<PagingData<CharacterExtraData>> =
        _characterId.flatMapLatest { generatePagerForStories(it).flow.cachedIn(viewModelScope) }

    val events: Flow<PagingData<CharacterExtraData>> =
        _characterId.flatMapLatest { generatePagerForEvents(it).flow.cachedIn(viewModelScope) }

    val character: Flow<Response<CharacterDataWrapper.CharacterDataContainer.Character>> =
        _characterId.map { repository.getCharacter(it) }

    fun setCharacterId(characterId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _characterId.emit(characterId)
        }
    }

    private fun generatePagerForComics(characterId: Int): Pager<PaginationKey, CharacterExtraData> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_CHARACTER_EXTRA_DATA_LIMIT),
            pagingSourceFactory = {
                CharacterExtraDataSource(loadData = {
                    repository.getCharacterComics(characterId = characterId, key = it)
                        .convertResponse { body -> body.toCharacterExtraData() }
                })
            }
        )
    }

    private fun generatePagerForSeries(characterId: Int): Pager<PaginationKey, CharacterExtraData> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_CHARACTER_EXTRA_DATA_LIMIT),
            pagingSourceFactory = {
                CharacterExtraDataSource(loadData = {
                    repository.getCharacterSeries(characterId = characterId, key = it)
                        .convertResponse { body -> body.toCharacterExtraData() }
                })
            }
        )
    }

    private fun generatePagerForEvents(characterId: Int): Pager<PaginationKey, CharacterExtraData> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_CHARACTER_EXTRA_DATA_LIMIT),
            pagingSourceFactory = {
                CharacterExtraDataSource(loadData = {
                    repository.getCharacterEvents(characterId = characterId, key = it)
                        .convertResponse { body -> body.toCharacterExtraData() }
                })
            }
        )
    }

    private fun generatePagerForStories(characterId: Int): Pager<PaginationKey, CharacterExtraData> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_CHARACTER_EXTRA_DATA_LIMIT),
            pagingSourceFactory = {
                CharacterExtraDataSource(loadData = {
                    repository.getCharacterStories(characterId = characterId, key = it)
                        .convertResponse { body -> body.toCharacterExtraData() }
                })
            }
        )
    }
}