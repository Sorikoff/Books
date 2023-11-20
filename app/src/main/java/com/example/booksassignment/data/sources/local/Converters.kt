package com.example.booksassignment.data.sources.local

import androidx.room.TypeConverter
import java.time.OffsetDateTime

class Converters {

    @TypeConverter
    fun fromStringToOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let { OffsetDateTime.parse(it) }
    }

    @TypeConverter
    fun fromOffsetDateTimeToString(offsetDateTime: OffsetDateTime?): String? {
        return offsetDateTime?.toString()
    }
}
