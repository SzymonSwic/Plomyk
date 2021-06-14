package szymon.swic.plomyk.features.songs.data.repository

import szymon.swic.plomyk.core.api.RestApi
import szymon.swic.plomyk.core.exception.ErrorWrapper
import szymon.swic.plomyk.core.exception.callOrThrow
import szymon.swic.plomyk.core.network.NetworkStateProvider
import szymon.swic.plomyk.features.songs.data.local.SongDao
import szymon.swic.plomyk.features.songs.data.local.model.SongCached
import szymon.swic.plomyk.features.songs.domain.SongRepository
import szymon.swic.plomyk.features.songs.domain.model.Song

class SongRepositoryImpl(
    private val restApi: RestApi,
    private val songDao: SongDao,
    private val networkStateProvider: NetworkStateProvider,
    private val errorWrapper: ErrorWrapper
) : SongRepository {

    override suspend fun getSongs(): List<Song> = if (networkStateProvider.isNetworkAvailable()) {
        callOrThrow(errorWrapper) { getSongsFromRemote() }
            .also { saveSongsToLocal(it) }
    } else {
        getSongsFromLocal()
    }

    private suspend fun getSongsFromRemote(): List<Song> =
        restApi.getSongs().map { Song(it.title, it.author, it.lyrics) }

    private suspend fun saveSongsToLocal(songs: List<Song>) {
        songs.map { SongCached(it) }
            .toTypedArray()
            .let {
                songDao.deleteAll()
                songDao.saveSongs(*it)
            }
    }

    private suspend fun getSongsFromLocal(): List<Song> {
        return songDao.getSongs()
            .map { it.toSong() }
    }
}
