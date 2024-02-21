package com.org.gamecatalog.data.datasource.network.client

import com.org.gamecatalog.data.datasource.network.response.DetailGameResponse
import com.org.gamecatalog.data.datasource.network.response.GameResponse
import com.org.gamecatalog.data.datasource.network.response.GenreResponse
import com.org.gamecatalog.data.datasource.network.response.ListResponse

interface GameClient {
  suspend fun fetchSearchGame(page: Int, searchQuery: String): ListResponse<GameResponse>
  suspend fun fetchListGameByGenre(page: Int, genre: String?): ListResponse<GameResponse>
  suspend fun fetchGenres(): ListResponse<GenreResponse>
  suspend fun fetchDetailGame(gameId: Int): DetailGameResponse
}