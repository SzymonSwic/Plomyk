package szymon.swic.plomyk.core.network

interface NetworkStateProvider {
    fun isNetworkAvailable(): Boolean
}
