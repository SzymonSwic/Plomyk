package szymon.swic.plomyk.core.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import szymon.swic.plomyk.core.exception.ErrorMapper
import szymon.swic.plomyk.core.exception.ErrorMapperImpl
import szymon.swic.plomyk.core.exception.ErrorWrapper
import szymon.swic.plomyk.core.exception.ErrorWrapperImpl
import szymon.swic.plomyk.core.network.NetworkStateProvider
import szymon.swic.plomyk.core.network.NetworkStateProviderImpl

val appModule = module {
    factory { LinearLayoutManager(androidContext()) }
    factory<ErrorWrapper> { ErrorWrapperImpl() }
    factory<ErrorMapper> { ErrorMapperImpl(androidContext()) }
    factory<NetworkStateProvider> { NetworkStateProviderImpl(get()) }
    factory {
        androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}
