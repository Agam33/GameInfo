package com.org.gamecatalog.data.datasource.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class SearchKeywordEntity(
  @PrimaryKey val keyword: String,
  val createdAt: LocalDateTime
)
