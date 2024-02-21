package com.org.gamecatalog.data.mapper

import com.org.gamecatalog.data.datasource.network.response.GenreResponse
import com.org.gamecatalog.data.model.Genre

fun GenreResponse.toModel(): Genre =
  Genre(id, name, slug)