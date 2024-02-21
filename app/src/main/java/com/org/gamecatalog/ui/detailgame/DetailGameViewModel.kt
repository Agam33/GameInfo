package com.org.gamecatalog.ui.detailgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.gamecatalog.data.model.Favorite
import com.org.gamecatalog.data.model.GamePlatform
import com.org.gamecatalog.data.model.Genre
import com.org.gamecatalog.data.model.Publisher
import com.org.gamecatalog.data.model.Rating
import com.org.gamecatalog.data.repository.favorite.FavoriteRepository
import com.org.gamecatalog.data.repository.game.GameRepository
import com.org.gamecatalog.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class DetailGameViewModel @Inject constructor(
  private val gameRepository: GameRepository,
  private val favoriteRepository: FavoriteRepository
): ViewModel() {

  private var _detailGameItemUiState = MutableStateFlow<UiState<DetailGameItemUiState>>(UiState.Loading)
  val detailGameUiState: StateFlow<UiState<DetailGameItemUiState>> = _detailGameItemUiState

  private var _isFavorite = MutableStateFlow<Boolean>(false)
  val isFavorite: StateFlow<Boolean> = _isFavorite

  fun getDetailGame(gameId: Int) {
    viewModelScope.launch {
      _detailGameItemUiState.emit(UiState.Loading)
      try {
        val data = gameRepository.getDetailGame(gameId)

        val detailItem = DetailGameItemUiState(
          id = data.id,
          name = data.name ?: "",
          description = data.description ?: "",
          backgroundImage = data.backgroundImage ?: "",
          rating = data.rating ?: 0.0,
          websiteUrl = data.websiteUrl ?: ""
        )

        setRating(data.ratings, detailItem)
        setGenre(data.genres, detailItem)
        setPlatform(data.platforms, detailItem)
        setPublisher(data.publishers, detailItem)

        getFavoriteById(data.id)

        _detailGameItemUiState.emit(UiState.Success(detailItem))
      } catch (e: Exception) {
        _detailGameItemUiState.emit(UiState.Error(e.message.toString()))
      }
    }
  }

  private fun getFavoriteById(id: Int) {
    viewModelScope.launch {
      favoriteRepository.getFavoriteByIdWithFlow(id).collect {
        _isFavorite.emit(it?.isFavorite ?: false)
      }
    }
  }

  fun setFavorite(favorite: Favorite) {
    viewModelScope.launch {
      val f = favoriteRepository.getFavoriteId(favorite.id)
      if(f == null) {
        favoriteRepository.insertFavorite(favorite)
      } else {
        favoriteRepository.deleteFavorite(favorite)
      }
    }
  }

  private fun setGenre(genres: List<Genre>?, detailGameItemUiState: DetailGameItemUiState) {
    if(genres.isNullOrEmpty()) return

    val strGenres = StringBuilder()
    for(genre in genres) {
      strGenres.append("${genre.name}, ")
    }

    if(strGenres.isNotEmpty()) {
      strGenres.deleteAt(strGenres.length - 2)
    }

    detailGameItemUiState.genres = strGenres.trim().toString()
  }

  private fun setPlatform(platforms: List<GamePlatform>?, detailGameItemUiState: DetailGameItemUiState) {
    if(platforms.isNullOrEmpty()) return

    val strPlatform = StringBuilder()
    for(platform in platforms) {
      strPlatform.append("${platform.platform?.name}, ")
    }

    if(strPlatform.isNotEmpty()) {
      strPlatform.deleteAt(strPlatform.length - 2)
    }

    detailGameItemUiState.platforms = strPlatform.trim().toString()
  }

  private fun setRating(ratings: List<Rating>?, detailGameItemUiState: DetailGameItemUiState) {
    if(ratings.isNullOrEmpty()) return

    var totalReview = 0
    for(rating in ratings) {
      totalReview += rating.count ?: 0
      when(rating.title?.lowercase()) {
        "recommended" -> detailGameItemUiState.recommendedVal = rating.percent?.roundToInt() ?: 0
        "exceptional" -> detailGameItemUiState.exceptionalVal = rating.percent?.roundToInt() ?: 0
        "meh" -> detailGameItemUiState.mehVal = rating.percent?.roundToInt() ?: 0
        "skip" -> detailGameItemUiState.skipVal = rating.percent?.roundToInt() ?: 0
      }
    }

    detailGameItemUiState.totalReview = totalReview
  }

  private fun setPublisher(publishers: List<Publisher>?, detailGameItemUiState: DetailGameItemUiState) {
    if(publishers.isNullOrEmpty()) return

    val strPublisher = StringBuilder()
    for(publisher in publishers) {
      strPublisher.append("${publisher.name}, ")
    }

    if(strPublisher.isNotEmpty()) {
      strPublisher.deleteAt(strPublisher.length - 2)
    }

    detailGameItemUiState.publishers = strPublisher.trim().toString()
  }
}

data class DetailGameItemUiState(
  var id: Int = 0,
  var name: String = "",
  var description: String = "",
  var backgroundImage: String = "",
  var rating: Double = 0.0,
  var platforms: String = "-",
  var genres: String = "-",
  var exceptionalVal: Int = 0,
  var recommendedVal: Int = 0,
  var mehVal: Int = 0,
  var skipVal: Int = 0,
  var totalReview: Int = 0,
  var websiteUrl: String = "-",
  var publishers: String = "-",
)