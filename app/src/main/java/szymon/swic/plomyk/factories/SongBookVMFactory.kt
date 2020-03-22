package szymon.swic.plomyk.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import szymon.swic.plomyk.model.SongRepository
import szymon.swic.plomyk.viewmodel.SongBookVM

class SongBookVMFactory(private val songRepository: SongRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SongBookVM(songRepository) as T
    }

}