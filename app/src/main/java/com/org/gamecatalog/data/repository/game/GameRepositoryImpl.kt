package com.org.gamecatalog.data.repository.game

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.org.gamecatalog.data.datasource.network.client.GameClient
import com.org.gamecatalog.data.mapper.toModel
import com.org.gamecatalog.data.model.DetailGame
import com.org.gamecatalog.data.model.Game
import com.org.gamecatalog.paging.ListGameByGenrePagingSource
import com.org.gamecatalog.paging.SearchGamePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
  private val gameClient: GameClient,
): GameRepository {

  override fun searchGame(searchQuery: String): Flow<PagingData<Game>> {
    return Pager(
      config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
      initialKey = null,
      pagingSourceFactory = { SearchGamePagingSource(gameClient, searchQuery) }
    ).flow
  }

  override fun getListGameByGenre(genre: String?): Flow<PagingData<Game>> {
    return Pager(
      config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
      initialKey = null,
      pagingSourceFactory = { ListGameByGenrePagingSource(gameClient, genre) }
    ).flow
  }

  override suspend fun getDetailGame(gameId: Int): DetailGame {
    return gameClient.fetchDetailGame(gameId).toModel()
  }

  companion object {
    private const val NETWORK_PAGE_SIZE = 20
    private const val TAG = "GameRepositoryImpl"
  }
}