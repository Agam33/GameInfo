package com.org.gamecatalog.di

import com.org.gamecatalog.adapter.GameAdapter
import com.org.gamecatalog.adapter.ItemSearchKeywordAdapter
import com.org.gamecatalog.adapter.ItemSearchResultAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideGameAdapter(): GameAdapter = GameAdapter()

    @Provides
    fun provideItemSearchResultAdapter(): ItemSearchResultAdapter = ItemSearchResultAdapter()

    @Provides
    fun provideItemSearchKeywordAdapter(): ItemSearchKeywordAdapter = ItemSearchKeywordAdapter()
}