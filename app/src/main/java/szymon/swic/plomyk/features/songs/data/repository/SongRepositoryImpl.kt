package szymon.swic.plomyk.features.songs.data.repository

import szymon.swic.plomyk.core.api.RestApi
import szymon.swic.plomyk.core.exception.ErrorWrapper
import szymon.swic.plomyk.core.exception.callOrThrow
import szymon.swic.plomyk.features.songs.domain.SongRepository
import szymon.swic.plomyk.features.songs.domain.model.Song


class SongRepositoryImpl(
    private val restApi: RestApi,
    private val errorWrapper: ErrorWrapper
) : SongRepository {

    override suspend fun getSongs(): List<Song> {
        return callOrThrow(errorWrapper) { getSongsFromRemote() }
    }

    private suspend fun getSongsFromRemote(): List<Song> =
        restApi.getSongs().map { Song(it.title, it.author, it.lyrics) }
}
