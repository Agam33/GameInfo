package com.org.gamecatalog.data.mapper

import com.org.gamecatalog.data.datasource.network.response.RatingResponse
import com.org.gamecatalog.data.model.Rating

fun RatingResponse.toModel(): Rating =
  Rating(id, title, count, percent)