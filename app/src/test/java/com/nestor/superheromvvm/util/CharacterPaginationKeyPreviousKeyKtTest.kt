package com.nestor.superheromvvm.util

import com.nestor.superheromvvm.data.repository.character.CharacterPaginationKey
import com.nestor.superheromvvm.data.repository.character.DEFAULT_CHARACTER_LIMIT
import org.junit.Assert.*

import org.junit.Test

class CharacterPaginationKeyPreviousKeyKtTest {

    @Test
    fun `should return the previous key`() {
        val key = CharacterPaginationKey(offset = 90, limit = DEFAULT_CHARACTER_LIMIT)
        assertEquals(
            key.previousKey(),
            CharacterPaginationKey(
                offset = 90 - DEFAULT_CHARACTER_LIMIT,
                limit = DEFAULT_CHARACTER_LIMIT
            )
        )
    }

    @Test
    fun `should return null because theres no prev key`() {
        val key = CharacterPaginationKey(offset = 0, limit = DEFAULT_CHARACTER_LIMIT)
        assertNull(key.previousKey())
    }
}