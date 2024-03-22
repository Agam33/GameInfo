package com.org.gamecatalog.di.network

import android.content.Context
import com.org.gamecatalog.data.datasource.network.service.GameApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
  fun provideOkHttpClient(networkManager: NetworkManager): OkHttpClient {
    return OkHttpClient
      .Builder()
      .addInterceptor(ConnectionInterceptor(networkManager))
      .addInterceptor(HttpRequestInterceptor())
      .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
      .connectTimeout(60, TimeUnit.SECONDS)
      .readTimeout(60, TimeUnit.SECONDS)
      .connectionPool(ConnectionPool(0, 60, TimeUnit.SECONDS))
      .build()
  }

  @Provides
  @Singleton
  fun provideGameApiService(retrofit: Retrofit): GameApiService {
    return retrofit.create(GameApiService::class.java)
  }

  @Provides
  @Singleton
  fun provideNetworkManager(@ApplicationContext context: Context): NetworkManager =
      NetworkManager(context)
}