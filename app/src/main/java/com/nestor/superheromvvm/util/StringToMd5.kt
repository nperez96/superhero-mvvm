package com.nestor.superheromvvm.util

import java.security.MessageDigest

fun String.toMd5(): String {
    return MessageDigest
        .getInstance("MD5")
        .digest(this.toByteArray())
        .joinToString("") { "%02x".format(it) }
}