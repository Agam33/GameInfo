package com.org.gamecatalog.di.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import java.io.IOException

class NetworkManager(private val context: Context) {

    private val connectivityManager
        get() = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    var connectivityStatusListener: ConnectivityStatusListener? = null

    private val networkRequest
        get() = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_BLUETOOTH)
            .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            connectivityStatusListener?.isOnline(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            connectivityStatusListener?.isOnline(false)
        }
    }

    fun initRequest() {
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    fun isInternetAvailable(): Boolean {
        val network: Network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }

    data class NoInternetException(private var msg: String = "No Internet Connection!."): IOException(msg)

    interface ConnectivityStatusListener {
        fun isOnline(status: Boolean)
    }
}