package com.org.gamecatalog.data.repository.game

import androidx.paging.PagingData
import com.org.gamecatalog.data.model.DetailGame
import com.org.gamecatalog.data.model.Game
import kotlinx.coroutines.flow.Flow

interface GameRepository {
  fun searchGame(searchQuery: String): Flow<PagingData<Game>>
  fun getListGameByGenre(genre: String?): Flow<PagingData<Game>>
  suspend fun getDetailGame(gameId: Int):DetailGame
}