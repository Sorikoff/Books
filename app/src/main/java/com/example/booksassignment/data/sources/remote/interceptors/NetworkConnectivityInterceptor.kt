package com.example.booksassignment.data.sources.remote.interceptors

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Attempts to detect missing network connection to warn user proactively by throwing [NoConnectivityException].
 *
 * @throws [NoConnectivityException]
 */
class NetworkConnectivityInterceptor @Inject constructor(
    private val applicationContext: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkAvailable()) {
            throw NoConnectivityException()
        } else {
            return chain.proceed(chain.request())
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        val internet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        val validated = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        return internet && validated
    }
}

/**
 * Signals that there is no network connection present on device.
 */
class NoConnectivityException : IOException("No internet connection found.")
