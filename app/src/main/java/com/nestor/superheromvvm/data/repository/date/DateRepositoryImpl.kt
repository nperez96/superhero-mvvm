package com.nestor.superheromvvm.data.repository.date

import java.util.*

class DateRepositoryImpl : DateRepository {
    override fun getCurrentTimestamp(): Long = Date().time
}