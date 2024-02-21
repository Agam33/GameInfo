package com.org.gamecatalog.data.datasource.network.response

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
  @field:SerializedName("count") val count: Int,
  @field:SerializedName("next") val next: String?,
  @field:SerializedName("previous") val previous: String?,
  @field:SerializedName("results") val result: List<T>
)