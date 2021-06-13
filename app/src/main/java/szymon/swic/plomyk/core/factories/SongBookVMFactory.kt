package szymon.swic.plomyk.core.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import szymon.swic.plomyk.features.songs.data.repository.SongRepository
import szymon.swic.plomyk.features.songs.list.presentation.SongBookViewModel

class SongBookVMFactory(private val songRepository: SongRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SongBookViewModel(songRepository) as T
    }

}