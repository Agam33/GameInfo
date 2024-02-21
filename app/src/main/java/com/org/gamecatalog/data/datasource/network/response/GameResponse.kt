package com.org.gamecatalog.data.datasource.network.response

import com.google.gson.annotations.SerializedName

data class GameResponse(
  @field:SerializedName("id") val id: Int,
  @field:SerializedName("name") val name: String?,
  @field:SerializedName("slug") val slug: String?,
  @field:SerializedName("released") val released: String?,
  @field:SerializedName("background_image") val backgroundImage: String?,
  @field:SerializedName("platforms") val gamePlatformResponses: List<GamePlatformResponse>?
)