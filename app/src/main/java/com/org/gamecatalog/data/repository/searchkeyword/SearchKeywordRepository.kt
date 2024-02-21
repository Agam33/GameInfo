package com.org.gamecatalog.data.repository.searchkeyword

import com.org.gamecatalog.data.model.SearchKeyword
import kotlinx.coroutines.flow.Flow

interface SearchKeywordRepository {
  suspend fun insert(searchKeyword: SearchKeyword)
  suspend fun delete(searchKeyword: SearchKeyword)
  fun findAllWithFlow(): Flow<List<SearchKeyword>>
}