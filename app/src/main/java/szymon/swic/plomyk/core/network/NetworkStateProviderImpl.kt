package szymon.swic.plomyk.core.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import szymon.swic.plomyk.core.network.NetworkStateProvider

class NetworkStateProviderImpl(
    private val connectivityManager: ConnectivityManager
) : NetworkStateProvider {

    override fun isNetworkAvailable(): Boolean {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    or capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    or capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
