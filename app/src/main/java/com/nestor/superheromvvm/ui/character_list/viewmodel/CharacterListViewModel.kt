package com.nestor.superheromvvm.ui.character_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nestor.superheromvvm.data.model.CharacterDataWrapper
import com.nestor.superheromvvm.data.repository.character.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {
    fun getCharacters(): Flow<PagingData<CharacterDataWrapper.CharacterDataContainer.Character>> =
        characterRepository.getCharacters().cachedIn(viewModelScope)
}