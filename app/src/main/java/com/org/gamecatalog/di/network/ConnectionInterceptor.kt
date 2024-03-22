package com.org.gamecatalog.di.network

import okhttp3.Interceptor
import okhttp3.Response

class ConnectionInterceptor(private val networkManager: NetworkManager): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!networkManager.isInternetAvailable()) {
            throw NetworkManager.NoInternetException()
        }
        return chain.proceed(chain.request())
    }
}