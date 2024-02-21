package com.org.gamecatalog.data.datasource.network.client

import com.org.gamecatalog.data.datasource.network.response.DetailGameResponse
import com.org.gamecatalog.data.datasource.network.response.GameResponse
import com.org.gamecatalog.data.datasource.network.response.GenreResponse
import com.org.gamecatalog.data.datasource.network.response.ListResponse
import com.org.gamecatalog.data.datasource.network.service.GameApiService
import javax.inject.Inject

class GameClientImpl @Inject constructor(
  private val gameApiService: GameApiService
): GameClient {

  override suspend fun fetchSearchGame(page: Int, searchQuery: String): ListResponse<GameResponse> {
    return gameApiService.fetchSearchGame(page = page, searchQuery = searchQuery)
  }

  override suspend fun fetchListGameByGenre(page: Int, genre: String?): ListResponse<GameResponse> {
    return gameApiService.fetchGameListByGenre(page = page, genre = genre)
  }

  override suspend fun fetchGenres(): ListResponse<GenreResponse> {
    return gameApiService.fetchGenres()
  }

  override suspend fun fetchDetailGame(gameId: Int): DetailGameResponse {
    return gameApiService.fetchDetailGame(gameId)
  }
}