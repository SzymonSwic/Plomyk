package szymon.swic.plomyk.features.tuner

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import szymon.swic.plomyk.features.splash.SplashScreenFragment

val tunerModule = module {

    // data

    // domain

    // presentation
    factory { TunerFragment() }
    viewModel { TunerViewModel() }
}
