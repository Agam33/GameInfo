package com.org.gamecatalog.data.repository.genre

import com.org.gamecatalog.data.datasource.network.client.GameClient
import com.org.gamecatalog.data.mapper.toModel
import com.org.gamecatalog.data.model.Genre
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
  private val gameClient: GameClient
): GenreRepository {

  override suspend fun getListGenre(): List<Genre> {
    return gameClient.fetchGenres().result.map { it.toModel() }
  }

  companion object {
    private const val TAG = "GenreRepositoryImpl"
  }
}