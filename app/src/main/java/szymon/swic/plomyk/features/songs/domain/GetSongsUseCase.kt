package szymon.swic.plomyk.features.songs.domain

import szymon.swic.plomyk.core.base.UseCase
import szymon.swic.plomyk.model.Song
import szymon.swic.plomyk.model.SongRepository

class GetSongsUseCase(private val songRepository: SongRepository) :
    UseCase<List<Song>, Unit>() {

    override suspend fun action(params: Unit): List<Song> =
        songRepository.getSongsFromApi()
}
