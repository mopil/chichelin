package com.chichelin.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val PAGE_PER_UNIT = 8

inline fun <reified T> T.logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}

fun getSavePath(filename: String? = null): String {
    return filename?.let {
        System.getProperty("user.dir") + "/image/${filename}"
    } ?: run {
        System.getProperty("user.dir") + "/image/"
    }
}
