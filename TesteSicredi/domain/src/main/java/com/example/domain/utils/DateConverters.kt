package com.example.domain.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

object DateConverters {
    @JvmStatic
    fun localDateTimeTimeToLong(date: LocalDateTime): Long {
        return date.toInstant(ZoneOffset.UTC).epochSecond
    }

    @JvmStatic
    fun longToLocalDateTime(value: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(value), ZoneId.of("UTC"))
    }
}