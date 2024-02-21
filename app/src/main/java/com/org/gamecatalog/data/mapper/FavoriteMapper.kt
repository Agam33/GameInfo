package com.org.gamecatalog.data.mapper

import com.org.gamecatalog.data.datasource.database.entity.FavoriteEntity
import com.org.gamecatalog.data.model.Favorite

fun FavoriteEntity.toModel(): Favorite =
  Favorite(id, name, rating, backgroundImage, isFavorite, createdAt)

fun Favorite.toEntity(): FavoriteEntity =
  FavoriteEntity(id, name, rating, backgroundImage, isFavorite, createdAt)