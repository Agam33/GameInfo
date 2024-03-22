package com.org.gamecatalog.di.repository

import com.org.gamecatalog.data.repository.favorite.FavoriteRepository
import com.org.gamecatalog.data.repository.favorite.FavoriteRepositoryImpl
import com.org.gamecatalog.data.repository.game.GameRepository
import com.org.gamecatalog.data.repository.game.GameRepositoryImpl
import com.org.gamecatalog.data.repository.genre.GenreRepository
import com.org.gamecatalog.data.repository.genre.GenreRepositoryImpl
import com.org.gamecatalog.data.repository.searchkeyword.SearchKeywordRepository
import com.org.gamecatalog.data.repository.searchkeyword.SearchKeywordRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
  @Binds
  abstract fun bindGameRepository(gameRepositoryImpl: GameRepositoryImpl): GameRepository

  @Binds
  abstract fun bindGenreRepository(genreRepositoryImpl: GenreRepositoryImpl): GenreRepository

  @Binds
  abstract fun bindFavoriteRepository(favoriteRepositoryImpl: FavoriteRepositoryImpl): FavoriteRepository

  @Binds
  abstract fun bindSearchKeywordRepository(searchKeywordRepositoryImpl: SearchKeywordRepositoryImpl): SearchKeywordRepository
}