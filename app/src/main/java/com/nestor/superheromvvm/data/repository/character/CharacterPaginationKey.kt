package com.nestor.superheromvvm.data.repository.character

const val DEFAULT_CHARACTER_LIMIT = 20

data class CharacterPaginationKey(val limit: Int, val offset: Int)
