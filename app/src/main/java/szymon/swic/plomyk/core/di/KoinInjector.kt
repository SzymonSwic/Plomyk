package szymon.swic.plomyk.core.di

val koinInjector = featureModules
    .plus(appModule)
    .plus(networkModule)
