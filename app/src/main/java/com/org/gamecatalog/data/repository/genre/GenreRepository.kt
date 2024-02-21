package com.org.gamecatalog.data.repository.genre

import com.org.gamecatalog.data.model.Genre

interface GenreRepository {
  suspend fun getListGenre(): List<Genre>
}