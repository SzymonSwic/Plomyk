package szymon.swic.plomyk.features.songs.domain

import szymon.swic.plomyk.core.base.UseCase
import szymon.swic.plomyk.features.songs.domain.model.Song
import szymon.swic.plomyk.features.songs.data.repository.SongRepositoryImpl

class GetSongsUseCase(private val songRepository: SongRepositoryImpl) :
    UseCase<List<Song>, Unit>() {

    override suspend fun action(params: Unit): List<Song> = songRepository.getSongs()
}
