package com.org.gamecatalog.data.mapper

import com.org.gamecatalog.data.datasource.network.response.GamePlatformResponse
import com.org.gamecatalog.data.datasource.network.response.PlatformResponse
import com.org.gamecatalog.data.model.GamePlatform
import com.org.gamecatalog.data.model.Platform


fun GamePlatformResponse.toModel(): GamePlatform =
  GamePlatform(platformResponse?.toModel(), releasedAt, requirementResponse?.toModel())

fun PlatformResponse.toModel(): Platform =
  Platform(id, name, slug)