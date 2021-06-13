package szymon.swic.plomyk.core.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import szymon.swic.plomyk.core.exception.ErrorMapper
import szymon.swic.plomyk.core.exception.ErrorMapperImpl
import szymon.swic.plomyk.core.exception.ErrorWrapper
import szymon.swic.plomyk.core.exception.ErrorWrapperImpl

val appModule = module {
    factory<ErrorWrapper> { ErrorWrapperImpl() }
    factory<ErrorMapper> { ErrorMapperImpl(androidContext()) }
}
