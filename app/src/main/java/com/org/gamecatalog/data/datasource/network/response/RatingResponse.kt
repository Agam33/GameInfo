package com.org.gamecatalog.data.datasource.network.response

import com.google.gson.annotations.SerializedName

data class RatingResponse(
  @field:SerializedName("id") val id: Int,
  @field:SerializedName("title") val title: String,
  @field:SerializedName("count") val count: Int,
  @field:SerializedName("percent") val percent: Double
)