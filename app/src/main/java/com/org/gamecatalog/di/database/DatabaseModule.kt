package com.org.gamecatalog.di.database

import android.content.Context
import androidx.room.Room
import com.org.gamecatalog.data.datasource.database.GameDatabase
import com.org.gamecatalog.data.datasource.database.dao.FavoriteDao
import com.org.gamecatalog.data.datasource.database.dao.SearchKeywordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Provides
  @Singleton
  fun provideDatabase(@ApplicationContext context: Context): GameDatabase {
    return Room.databaseBuilder(
      context,
      GameDatabase::class.java,
      "game_info.db"
    )
      .build()
  }

  @Provides
  @Singleton
  fun provideFavoriteDao(gameDb: GameDatabase): FavoriteDao = gameDb.favoriteDao()

  @Provides
  @Singleton
  fun provideSearchKeywordDao(gameDb: GameDatabase): SearchKeywordDao = gameDb.searchKeyword()
}