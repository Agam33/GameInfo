package com.org.gamecatalog.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.org.gamecatalog.data.model.Game
import com.org.gamecatalog.data.model.Genre
import com.org.gamecatalog.data.model.SearchKeyword
import com.org.gamecatalog.data.repository.game.GameRepository
import com.org.gamecatalog.data.repository.genre.GenreRepository
import com.org.gamecatalog.data.repository.searchkeyword.SearchKeywordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val gameRepository: GameRepository,
  private val genreRepository: GenreRepository,
  private val searchKeywordRepository: SearchKeywordRepository
): ViewModel() {

  private var _listGenreState = MutableStateFlow<List<Genre>>(emptyList())
  val listGenreState: StateFlow<List<Genre>> = _listGenreState

  private var _listGameState = MutableStateFlow<PagingData<Game>>(PagingData.empty())
  val listGameState: StateFlow<PagingData<Game>> = _listGameState

  private var _searchGameState = MutableStateFlow<PagingData<Game>>(PagingData.empty())
  val searchGameState: StateFlow<PagingData<Game>> = _searchGameState

  private var _searchKeywordState = MutableStateFlow<List<SearchKeyword>>(emptyList())
  val searchKeywordState: StateFlow<List<SearchKeyword>> = _searchKeywordState

  fun searchGame(searchQuery: String) {
    viewModelScope.launch {
      gameRepository.searchGame(searchQuery).collect {
        _searchGameState.emit(it)
      }
    }
  }

  fun getListGame(genreQuery: String?) {
    viewModelScope.launch {
      gameRepository.getListGameByGenre(genreQuery).collect {
        _listGameState.value = it
      }
    }
  }

  fun getListGenre() {
    viewModelScope.launch {
      _listGenreState.emit(genreRepository.getListGenre())
    }
  }

  fun getListSearchKeyword() {
    viewModelScope.launch {
      searchKeywordRepository.findAllWithFlow().collect {
        _searchKeywordState.emit(it)
      }
    }
  }

  fun deleteSearchKeyword(searchKeyword: SearchKeyword) {
    viewModelScope.launch {
      searchKeywordRepository.delete(searchKeyword)
    }
  }

  fun insertSearchKeyword(searchKeyword: SearchKeyword) {
    viewModelScope.launch {
      searchKeywordRepository.insert(searchKeyword)
    }
  }
}