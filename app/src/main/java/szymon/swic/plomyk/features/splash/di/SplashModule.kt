package szymon.swic.plomyk.features.splash.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import szymon.swic.plomyk.features.songs.navigation.SongNavigator
import szymon.swic.plomyk.features.songs.navigation.SongNavigatorImpl
import szymon.swic.plomyk.features.splash.navigation.SplashNavigator
import szymon.swic.plomyk.features.splash.navigation.SplashNavigatorImpl
import szymon.swic.plomyk.features.splash.presentation.SplashScreenFragment
import szymon.swic.plomyk.features.splash.presentation.SplashScreenViewModel

val splashModule = module {

    // data

    // domain

    // navigation
    factory<SplashNavigator> { SplashNavigatorImpl(get()) }

    // presentation
    factory { SplashScreenFragment() }
    viewModel { SplashScreenViewModel(get(), get()) }
}
