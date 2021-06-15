package szymon.swic.plomyk.core.di

import szymon.swic.plomyk.features.songs.di.songModule
import szymon.swic.plomyk.features.splash.di.splashModule
import szymon.swic.plomyk.features.tuner.di.tunerModule

val featureModules = listOf(
    splashModule,
    songModule,
    tunerModule
)
