package com.org.gamecatalog.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.gamecatalog.data.model.Favorite
import com.org.gamecatalog.data.repository.favorite.FavoriteRepository
import com.org.gamecatalog.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
  private val favoriteRepository: FavoriteRepository
): ViewModel() {

  private val _listFavorite = MutableStateFlow<UiState<List<Favorite>>>(UiState.Loading)
  val listFavorite: StateFlow<UiState<List<Favorite>>> = _listFavorite

  init {
    getFavorites()
  }

  private fun getFavorites() {
    viewModelScope.launch {
      favoriteRepository.getListFavorite().collect {
        _listFavorite.emit(UiState.Loading)
        try {
          if(it.isEmpty()) {
            _listFavorite.emit(UiState.Empty)
            return@collect
          }

          _listFavorite.emit(UiState.Success(it))

        } catch (e: Exception) {
          _listFavorite.emit(UiState.Error(e.message.toString()))
        }
      }
    }
  }

  fun deleteFavorite(favorite: Favorite) {
    viewModelScope.launch {
      favoriteRepository.deleteFavorite(favorite)
    }
  }
}