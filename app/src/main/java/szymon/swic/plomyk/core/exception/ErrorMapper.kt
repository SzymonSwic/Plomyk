package szymon.swic.plomyk.core.exception

interface ErrorMapper {
    fun map(throwable: Throwable): String
}
