package com.org.gamecatalog.data.model

import java.time.LocalDateTime

data class Favorite(
  var id: Int = -1,
  var name: String = "",
  var rating: Double = 0.0,
  var backgroundImage: String = "",
  var isFavorite: Boolean = false,
  val createdAt: LocalDateTime
)
