package com.nestor.superheromvvm.util

import org.junit.Assert.*
import org.junit.Test
import java.util.Collections

class StringToMd5KtTest {
    /**
     * The result should be 4fcce3cac8f8af57bc5d2a6b76d78200 for the given digest 12345150f4cccf722f319e9f0329b039227c4fb6e067ce491e7f6ea12527452affb9f6b93ef4
     */
    @Test
    fun `should generate the correct hash`() {
        val testing = "12345150f4cccf722f319e9f0329b039227c4fb6e067ce491e7f6ea12527452affb9f6b93ef4"

        val result = testing.toMd5()

        assertEquals(result, "4fcce3cac8f8af57bc5d2a6b76d78200")
    }

    @Test
    fun `migratory birds success`() {
        val arr = arrayOf(
            1, 4, 4, 4, 5, 3, 3, 3
        )
        val result = BirdsUtils.migratoryBirds(arr)

        assertEquals(result, 3)
    }

}