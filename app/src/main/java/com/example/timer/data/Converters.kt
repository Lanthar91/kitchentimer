package com.example.timer.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun durationsToString(list: List<Long>): String = list.joinToString(",")

    @TypeConverter
    fun stringToDurations(value: String): List<Long> =
        if (value.isBlank()) emptyList() else value.split(",").map { it.toLong() }
}
