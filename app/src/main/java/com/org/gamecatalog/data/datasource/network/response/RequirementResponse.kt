package com.org.gamecatalog.data.datasource.network.response

import com.google.gson.annotations.SerializedName

data class RequirementResponse(
  @field:SerializedName("minimum") val minimum: String?,
  @field:SerializedName("recommended") val recommended: String?
)