package com.org.gamecatalog.data.datasource.network.response

import com.google.gson.annotations.SerializedName

data class PublisherResponse(
  @field:SerializedName("id") val id: Int,
  @field:SerializedName("name") val name: String?
)
