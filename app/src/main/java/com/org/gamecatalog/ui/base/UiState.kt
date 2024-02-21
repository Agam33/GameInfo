package com.org.gamecatalog.ui.base

sealed class UiState<out T> {
  data class Error<T>(val message: String) : UiState<T>()
  data object Loading : UiState<Nothing>()
  data object Empty: UiState<Nothing>()
  data class Success<T>(val data: T): UiState<T>()
}