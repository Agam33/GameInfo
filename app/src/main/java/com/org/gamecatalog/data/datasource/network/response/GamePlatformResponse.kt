package com.org.gamecatalog.data.datasource.network.response

import com.google.gson.annotations.SerializedName

data class GamePlatformResponse(
  @field:SerializedName("platform") val platformResponse: PlatformResponse?,
  @field:SerializedName("released_at") val releasedAt: String?,
  @field:SerializedName("requirements") val requirementResponse: RequirementResponse?
)
