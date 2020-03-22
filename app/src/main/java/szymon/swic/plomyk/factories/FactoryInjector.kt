package szymon.swic.plomyk.factories

import szymon.swic.plomyk.model.SongRepository

object FactoryInjector {

    fun getSongBookVMFactory(): SongBookVMFactory {
        val songRepository = SongRepository.getInstance()
        return SongBookVMFactory(songRepository)
    }
}