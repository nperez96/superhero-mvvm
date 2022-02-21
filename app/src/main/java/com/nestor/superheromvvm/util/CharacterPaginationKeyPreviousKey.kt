package com.nestor.superheromvvm.util

import com.nestor.superheromvvm.data.repository.character.PaginationKey
import com.nestor.superheromvvm.data.repository.character.DEFAULT_CHARACTER_LIMIT

fun PaginationKey.previousKey(): PaginationKey? {
    return if (this.offset < DEFAULT_CHARACTER_LIMIT) {
        null
    } else {
        this.copy(offset = this.offset - DEFAULT_CHARACTER_LIMIT)
    }
}