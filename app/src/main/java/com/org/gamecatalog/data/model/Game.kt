package com.org.gamecatalog.data.model

data class Game(
  val id: Int,
  var name: String? = null,
  var slug: String? = null,
  var released: String? = null,
  var backgroundImage: String? = null,
  var gamePlatforms: List<GamePlatform>? = null
)