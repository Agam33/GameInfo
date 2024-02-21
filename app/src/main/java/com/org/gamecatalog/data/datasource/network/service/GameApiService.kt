package com.org.gamecatalog.data.datasource.network.service

import com.org.gamecatalog.data.datasource.network.response.DetailGameResponse
import com.org.gamecatalog.data.datasource.network.response.GameResponse
import com.org.gamecatalog.data.datasource.network.response.GenreResponse
import com.org.gamecatalog.data.datasource.network.response.ListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameApiService {

  @GET("games/{gameId}")
  suspend fun fetchDetailGame(
    @Path("gameId") gameId: Int
  ): DetailGameResponse

  @GET("games")
  suspend fun fetchSearchGame(
    @Query("page_size") limit: Int = 10,
    @Query("page") page: Int,
    @Query("search") searchQuery: String
  ): ListResponse<GameResponse>

  @GET("games")
  suspend fun fetchGameListByGenre(
    @Query("page_size") limit: Int = 20,
    @Query("page") page: Int,
    @Query("genres") genre: String?
  ): ListResponse<GameResponse>

  @GET("genres")
  suspend fun fetchGenres(
    @Query("page_size") limit: Int = 20,
    @Query("page") page: Int = 1
  ): ListResponse<GenreResponse>
}