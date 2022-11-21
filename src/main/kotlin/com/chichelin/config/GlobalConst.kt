package com.chichelin.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val PAGE_PER_UNIT = 8

inline fun <reified T> T.logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}

fun getSavePath(filename: String? = null): String {
    val os = System.getProperty("os.name")
    val savedPath = if (os.contains("linux")) {
        "///home/ubuntu/chichelin/image/"
    } else {
        System.getProperty("user.dir") + "/image/"
    }
    return filename?.let {
        savedPath + "$filename"
    } ?: run {
        savedPath
    }
}
