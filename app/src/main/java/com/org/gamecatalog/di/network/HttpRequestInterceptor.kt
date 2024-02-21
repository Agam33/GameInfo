package com.org.gamecatalog.di.network

import com.org.gamecatalog.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HttpRequestInterceptor: Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request().newBuilder()
    val oldUrl = chain.request().url
    val newUrl = oldUrl.newBuilder()
      .addQueryParameter("key", BuildConfig.API_KEY)
      .build()
    request.url(newUrl)
    return chain.proceed(request.build())
  }
}