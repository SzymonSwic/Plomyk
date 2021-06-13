package szymon.swic.plomyk.features.tuner

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tunerModule = module {
    viewModel { TunerViewModel() }
}
