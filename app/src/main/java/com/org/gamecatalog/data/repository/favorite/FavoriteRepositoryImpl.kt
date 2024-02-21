package com.org.gamecatalog.data.repository.favorite

import com.org.gamecatalog.data.datasource.database.dao.FavoriteDao
import com.org.gamecatalog.data.mapper.toEntity
import com.org.gamecatalog.data.mapper.toModel
import com.org.gamecatalog.data.model.Favorite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
  private val favoriteDao: FavoriteDao
): FavoriteRepository {

  override suspend fun insertFavorite(favorite: Favorite) {
    favoriteDao.insertFavorite(favorite.toEntity())
  }

  override suspend fun deleteFavorite(favorite: Favorite) {
    favoriteDao.deleteFavorite(favorite.toEntity())
  }

  override fun getListFavorite(): Flow<List<Favorite>> {
    return favoriteDao.findAll()
      .map {
        it.map { favorite ->
          favorite.toModel()
        }
      }
      .catch { e -> Timber.e(e) }
  }

  override fun getFavoriteByIdWithFlow(id: Int): Flow<Favorite?> {
    return favoriteDao.findByIdWithFlow(id)
      .map {
        it?.toModel()
      }
      .catch { Timber.e(it) }
  }

  override suspend fun getFavoriteId(id: Int): Favorite? {
    return favoriteDao.findById(id)?.toModel()
  }
}