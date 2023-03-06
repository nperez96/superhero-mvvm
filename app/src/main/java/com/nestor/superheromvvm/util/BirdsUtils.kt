package com.nestor.superheromvvm.util

import java.util.Collections

class BirdsUtils {

    companion object {
        fun migratoryBirds(arr: Array<Int>): Int {
            val map = mutableMapOf<Int, Int>()

            for (item in arr.sortedArray().distinct()) {
                map[item] = Collections.frequency(arr.toList(), item)
            }

            val maxBy = map.maxByOrNull { it.value }
            return maxBy?.key!!
        }
    }
}