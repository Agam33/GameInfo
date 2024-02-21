package com.org.gamecatalog.data.mapper

import com.org.gamecatalog.data.datasource.network.response.DetailGameResponse
import com.org.gamecatalog.data.datasource.network.response.GameResponse
import com.org.gamecatalog.data.model.DetailGame
import com.org.gamecatalog.data.model.Game

fun DetailGameResponse.toModel(): DetailGame =
  DetailGame(
    id,
    slug,
    name,
    nameOriginal,
    description,
    backgroundImage,
    rating,
    platforms?.map { it.toModel() },
    ratings?.map { it.toModel() },
    genres?.map { it.toModel() },
    websiteUrl,
    publishers?.map { it.toModel() }
  )

fun GameResponse.toModel(): Game =
  Game(
    id,
    name,
    slug,
    released,
    backgroundImage,
    gamePlatformResponses?.map { it.toModel() }
  )