package com.org.gamecatalog.di.network

import com.org.gamecatalog.data.datasource.network.client.GameClient
import com.org.gamecatalog.data.datasource.network.client.GameClientImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GameClientModule {

  @Binds
  @Singleton
  abstract fun bindGameClient(gameClientImpl: GameClientImpl): GameClient
}