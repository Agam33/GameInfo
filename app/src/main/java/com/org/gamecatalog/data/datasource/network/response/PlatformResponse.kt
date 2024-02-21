package com.org.gamecatalog.data.datasource.network.response

import com.google.gson.annotations.SerializedName

data class PlatformResponse(
  @field:SerializedName("id") val id: Int,
  @field:SerializedName("name") val name: String?,
  @field:SerializedName("slug") val slug: String?
)