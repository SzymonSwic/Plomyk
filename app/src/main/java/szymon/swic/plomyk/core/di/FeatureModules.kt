package szymon.swic.plomyk.core.di

import szymon.swic.plomyk.features.songs.di.songModule
import szymon.swic.plomyk.features.splash.splashModule
import szymon.swic.plomyk.features.tuner.tunerModule

val featureModules = listOf(
    splashModule,
    songModule,
    tunerModule
)
