package com.org.gamecatalog.data.repository.favorite

import com.org.gamecatalog.data.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
  suspend fun insertFavorite(favorite: Favorite)
  suspend fun deleteFavorite(favorite: Favorite)
  fun getListFavorite(): Flow<List<Favorite>>
  fun getFavoriteByIdWithFlow(id: Int): Flow<Favorite?>
  suspend fun getFavoriteId(id: Int): Favorite?
}