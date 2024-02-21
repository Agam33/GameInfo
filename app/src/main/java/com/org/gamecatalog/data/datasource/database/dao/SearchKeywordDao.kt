package com.org.gamecatalog.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.org.gamecatalog.data.datasource.database.entity.SearchKeywordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchKeywordDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertSearchKeyword(searchKeywordEntity: SearchKeywordEntity)

  @Delete
  suspend fun deleteSearchKeyword(searchKeywordEntity: SearchKeywordEntity)

  @Query("SELECT * FROM searchkeywordentity AS sk ORDER BY sk.createdAt DESC")
  fun findAllWithFlow(): Flow<List<SearchKeywordEntity>>
}