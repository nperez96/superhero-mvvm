package com.nestor.superheromvvm.data.repository.character

const val DEFAULT_CHARACTER_LIMIT = 20
const val DEFAULT_CHARACTER_EXTRA_DATA_LIMIT = 30

data class PaginationKey(val limit: Int, val offset: Int)
