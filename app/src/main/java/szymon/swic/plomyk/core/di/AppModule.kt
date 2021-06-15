package szymon.swic.plomyk.core.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import szymon.swic.plomyk.R
import szymon.swic.plomyk.core.exception.ErrorMapper
import szymon.swic.plomyk.core.exception.ErrorMapperImpl
import szymon.swic.plomyk.core.exception.ErrorWrapper
import szymon.swic.plomyk.core.exception.ErrorWrapperImpl
import szymon.swic.plomyk.core.navigation.FragmentNavigator
import szymon.swic.plomyk.core.navigation.FragmentNavigatorImpl
import szymon.swic.plomyk.core.network.NetworkStateProvider
import szymon.swic.plomyk.core.network.NetworkStateProviderImpl
import szymon.swic.plomyk.core.provider.ActivityProvider

val appModule = module {
    factory { LinearLayoutManager(androidContext()) }
    factory<ErrorWrapper> { ErrorWrapperImpl() }
    factory<ErrorMapper> { ErrorMapperImpl(androidContext()) }
    factory<NetworkStateProvider> { NetworkStateProviderImpl(get()) }
    factory {
        androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    single(createdAtStart = true) { ActivityProvider(androidApplication()) }
    factory<FragmentNavigator> {
        FragmentNavigatorImpl(
            activityProvider = get(),
            navHostFragmentRes = R.id.nav_host_fragment,
            homeDestinationRes = R.id.songListFragment,
            defaultNavOptions = get()
        )
    }
    factory {
        navOptions {
            anim { enter = android.R.anim.fade_in }
            anim { exit = android.R.anim.fade_out }
            anim { popEnter = android.R.anim.fade_in }
            anim { popExit = android.R.anim.fade_out }
        }
    }
}
