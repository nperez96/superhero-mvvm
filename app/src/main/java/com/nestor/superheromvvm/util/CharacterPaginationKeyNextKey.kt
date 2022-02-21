package com.nestor.superheromvvm.util

import com.nestor.superheromvvm.data.repository.character.PaginationKey
import com.nestor.superheromvvm.data.repository.character.DEFAULT_CHARACTER_LIMIT

fun PaginationKey.nextKey() = this.copy(offset = this.offset + DEFAULT_CHARACTER_LIMIT)