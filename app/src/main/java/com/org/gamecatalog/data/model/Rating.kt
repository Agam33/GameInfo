package com.org.gamecatalog.data.model

import com.google.gson.annotations.SerializedName

data class Rating(
  var id: Int,
  var title: String? = null,
  var count: Int? = null,
  var percent: Double? = null
)
