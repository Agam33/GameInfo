package com.org.gamecatalog.data.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.org.gamecatalog.data.datasource.network.client.GameClient
import com.org.gamecatalog.data.mapper.toModel
import com.org.gamecatalog.data.model.Game
import timber.log.Timber

class SearchGamePagingSource(
  private val gameClient: GameClient,
  private val searchQuery: String
): PagingSource<Int, Game>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
    val nextKey = params.key ?: 1
    return try {
      val networkResponse = gameClient.fetchSearchGame(nextKey, searchQuery)
      var nextPage: Int? = null
      networkResponse.next?.let {
        val uri = Uri.parse(it)
        nextPage = uri.getQueryParameter("page")?.toInt()
      }
      val result = networkResponse.result.map { it.toModel() }
      Timber.tag("ListGamePagingSource-load").d("Result size: ${result.size}}")
      LoadResult.Page (
        data =  result,
        prevKey = null,
        nextKey = nextPage
      )
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, Game>): Int? = null
}