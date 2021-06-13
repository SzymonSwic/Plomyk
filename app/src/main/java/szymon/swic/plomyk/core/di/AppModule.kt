package szymon.swic.plomyk.core.di

import org.koin.dsl.module
import szymon.swic.plomyk.core.exception.ErrorWrapper
import szymon.swic.plomyk.core.exception.ErrorWrapperImpl

val appModule = module {
    factory<ErrorWrapper> { ErrorWrapperImpl() }
}
