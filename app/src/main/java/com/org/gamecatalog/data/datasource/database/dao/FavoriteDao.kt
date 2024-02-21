package com.org.gamecatalog.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.org.gamecatalog.data.datasource.database.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

  @Insert
  suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

  @Delete
  suspend fun deleteFavorite(favoriteEntity: FavoriteEntity)

  @Query("SELECT * FROM favoriteentity as f ORDER BY f.createdAt DESC")
  fun findAll(): Flow<List<FavoriteEntity>>

  @Query("SELECT * FROM favoriteentity as f WHERE f.id = :id")
  fun findByIdWithFlow(id: Int): Flow<FavoriteEntity?>

  @Query("SELECT * FROM favoriteentity as f WHERE f.id = :id")
  suspend fun findById(id: Int): FavoriteEntity?
}