package com.org.gamecatalog.data.datasource.database

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class DbLocalDateTimeConverter {
  @TypeConverter
  fun dateToTimestamp(date: LocalDateTime?): Long? {
    return date?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
  }

  @TypeConverter
  fun fromTimestamp(value: Long?): LocalDateTime? {
    return value?.let {
      Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
  }
}