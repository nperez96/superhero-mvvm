package com.nestor.superheromvvm.util

import com.nestor.superheromvvm.data.repository.character.CharacterPaginationKey
import com.nestor.superheromvvm.data.repository.character.DEFAULT_CHARACTER_LIMIT

fun CharacterPaginationKey.previousKey(): CharacterPaginationKey? {
    return if (this.offset < DEFAULT_CHARACTER_LIMIT) {
        null
    } else {
        this.copy(offset = this.offset - DEFAULT_CHARACTER_LIMIT)
    }
}