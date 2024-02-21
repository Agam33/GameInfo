package com.org.gamecatalog.data.mapper

import com.org.gamecatalog.data.datasource.database.entity.SearchKeywordEntity
import com.org.gamecatalog.data.model.SearchKeyword

fun SearchKeywordEntity.toModel(): SearchKeyword =
  SearchKeyword(keyword, createdAt)

fun SearchKeyword.toEntity(): SearchKeywordEntity =
  SearchKeywordEntity(keyword, createdAt)