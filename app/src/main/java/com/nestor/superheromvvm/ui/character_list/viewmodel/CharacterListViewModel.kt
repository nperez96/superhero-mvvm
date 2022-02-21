package com.nestor.superheromvvm.ui.character_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nestor.superheromvvm.data.model.CharacterDataWrapper
import com.nestor.superheromvvm.data.repository.character.CharacterRepository
import com.nestor.superheromvvm.data.repository.character.DEFAULT_CHARACTER_LIMIT
import com.nestor.superheromvvm.ui.character_list.adapters.CharactersPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {

    val characters: Flow<PagingData<CharacterDataWrapper.CharacterDataContainer.Character>> =
        Pager(
            config = PagingConfig(pageSize = DEFAULT_CHARACTER_LIMIT),
            pagingSourceFactory = { CharactersPagingSource(characterRepository) },
        )
            .flow
            .cachedIn(viewModelScope)

}