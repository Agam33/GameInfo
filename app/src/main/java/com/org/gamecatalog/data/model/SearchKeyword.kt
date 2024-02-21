package com.org.gamecatalog.data.model

import java.time.LocalDateTime

data class SearchKeyword(
  var keyword: String,
  val createdAt: LocalDateTime,
)
