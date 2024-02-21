package com.org.gamecatalog.di.network

import com.org.gamecatalog.data.datasource.network.service.GameApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit
      .Builder()
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .baseUrl("https://api.rawg.io/api/")
      .build()
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient
      .Builder()
      .addInterceptor(HttpRequestInterceptor())
      .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
      .build()
  }

  @Provides
  @Singleton
  fun provideGameApiService(retrofit: Retrofit): GameApiService {
    return retrofit.create(GameApiService::class.java)
  }
}