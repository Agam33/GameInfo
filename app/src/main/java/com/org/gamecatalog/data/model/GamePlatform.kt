package com.org.gamecatalog.data.model

data class GamePlatform(
  var platform: Platform? = null,
  var releasedAt: String? = "",
  var requirement: Requirement? = null
)
