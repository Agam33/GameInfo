package com.org.gamecatalog.data.repository.searchkeyword

import com.org.gamecatalog.data.datasource.database.dao.SearchKeywordDao
import com.org.gamecatalog.data.mapper.toEntity
import com.org.gamecatalog.data.mapper.toModel
import com.org.gamecatalog.data.model.SearchKeyword
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class SearchKeywordRepositoryImpl @Inject constructor(
  private val searchKeywordDao: SearchKeywordDao
): SearchKeywordRepository {
  override suspend fun insert(searchKeyword: SearchKeyword) {
    searchKeywordDao.insertSearchKeyword(searchKeyword.toEntity())
  }

  override suspend fun delete(searchKeyword: SearchKeyword) {
    searchKeywordDao.deleteSearchKeyword(searchKeyword.toEntity())
  }

  override fun findAllWithFlow(): Flow<List<SearchKeyword>> {
    return searchKeywordDao.findAllWithFlow().map { list ->
      list.map { it.toModel() }
    }
      .catch { Timber.e(it) }
  }
}