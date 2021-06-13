package szymon.swic.plomyk.features.songs.domain

import szymon.swic.plomyk.features.songs.domain.model.Song

interface SongRepository {
    suspend fun getSongs(): List<Song>
}
