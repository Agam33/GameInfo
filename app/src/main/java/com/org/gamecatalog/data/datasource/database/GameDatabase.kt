package com.org.gamecatalog.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.org.gamecatalog.data.datasource.database.dao.FavoriteDao
import com.org.gamecatalog.data.datasource.database.dao.SearchKeywordDao
import com.org.gamecatalog.data.datasource.database.entity.FavoriteEntity
import com.org.gamecatalog.data.datasource.database.entity.SearchKeywordEntity

@Database(
  entities = [
    FavoriteEntity::class,
    SearchKeywordEntity::class
 ],
  version = 1,
  exportSchema = false
)
@TypeConverters(value = [DbLocalDateTimeConverter::class])
abstract class GameDatabase: RoomDatabase() {
  abstract fun favoriteDao(): FavoriteDao
  abstract fun searchKeyword(): SearchKeywordDao
}