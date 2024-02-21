package com.org.gamecatalog.data.datasource.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class FavoriteEntity(
  @PrimaryKey val id: Int,
  val name: String,
  val rating: Double,
  val backgroundImage: String,
  val isFavorite: Boolean,
  val createdAt: LocalDateTime
)
