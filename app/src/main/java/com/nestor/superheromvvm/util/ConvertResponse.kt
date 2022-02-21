package com.nestor.superheromvvm.util

import retrofit2.Response

fun <T, R> Response<T>.convertResponse(map: (T) -> R): Response<R> {
    return if (this.isSuccessful) {
        if (this.body() != null) {
            Response.success(this.code(), map(this.body()!!))
        } else {
            this as Response<R>
        }
    } else {
        this as Response<R>
    }
}