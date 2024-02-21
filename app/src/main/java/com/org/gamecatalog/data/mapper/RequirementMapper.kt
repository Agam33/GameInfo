package com.org.gamecatalog.data.mapper

import com.org.gamecatalog.data.datasource.network.response.RequirementResponse
import com.org.gamecatalog.data.model.Requirement

fun RequirementResponse.toModel(): Requirement =
  Requirement(minimum, recommended)