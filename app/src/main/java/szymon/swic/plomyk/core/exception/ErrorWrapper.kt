package szymon.swic.plomyk.core.exception

interface ErrorWrapper {
    fun wrap(throwable: Throwable): Throwable
}
