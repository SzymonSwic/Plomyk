package szymon.swic.plomyk.features.tuner.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import szymon.swic.plomyk.features.tuner.presentation.TunerFragment
import szymon.swic.plomyk.features.tuner.presentation.TunerViewModel

val tunerModule = module {

    // data

    // domain

    // presentation
    factory { TunerFragment() }
    viewModel { TunerViewModel() }
}
