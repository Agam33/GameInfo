package com.org.gamecatalog.data.mapper

import com.org.gamecatalog.data.datasource.network.response.PublisherResponse
import com.org.gamecatalog.data.model.Publisher

fun PublisherResponse.toModel(): Publisher =
  Publisher(
    id,
    name
  )