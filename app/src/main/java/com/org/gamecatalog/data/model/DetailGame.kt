package com.org.gamecatalog.data.model

data class DetailGame(
  val id: Int,
  var slug: String? = null,
  var name: String? = null,
  var nameOriginal: String? = null,
  var description: String? = null,
  var backgroundImage: String? = null,
  var rating: Double? = null,
  var platforms: List<GamePlatform>? = null,
  var ratings: List<Rating>? = null,
  var genres: List<Genre>? = null,
  var websiteUrl: String? = null,
  var publishers: List<Publisher>? = null
)