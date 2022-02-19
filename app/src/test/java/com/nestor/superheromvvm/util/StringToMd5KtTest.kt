package com.nestor.superheromvvm.util

import org.junit.Assert.*
import org.junit.Test

class StringToMd5KtTest {
    @Test
    fun `should generate the correct hash`() {
        val result = "4fcce3cac8f8af57bc5d2a6b76d78200"
        assertEquals(
            result,
            "12345150f4cccf722f319e9f0329b039227c4fb6e067ce491e7f6ea12527452affb9f6b93ef4".toMd5()
        )
    }

}