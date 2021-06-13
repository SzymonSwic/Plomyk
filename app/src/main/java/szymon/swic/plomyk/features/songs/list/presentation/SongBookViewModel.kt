package szymon.swic.plomyk.features.songs.list.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import szymon.swic.plomyk.core.exception.ErrorMapper
import szymon.swic.plomyk.core.base.BaseViewModel
import szymon.swic.plomyk.features.songs.domain.GetSongsUseCase
import szymon.swic.plomyk.features.songs.domain.model.Song

class SongBookViewModel(
    private val getSongsUseCase: GetSongsUseCase,
    errorMapper: ErrorMapper
) : BaseViewModel(errorMapper) {

    private val TAG = "SongBookVM"
    private var TestSongCounter = 1

    private val _songs by lazy {
        MutableLiveData<List<Song>>()
            .also { getSongs(it) }
    }

    val songs by lazy {
        _songs.map { songs ->
            songs.map { it }
        }
    }

    fun getFilteredSongs(text: String?) = songs.value?.filter { it ->
        listOf(it.title, it.author).any { field -> field.contains(text ?: "", ignoreCase = true) }
    } ?: listOf()

    private fun getSongs(songsLiveData: MutableLiveData<List<Song>>) {
        setPendingState()
        getSongsUseCase(
            params = Unit,
            scope = viewModelScope
        ) { result ->
            setIdleState()
            result.onSuccess { songsLiveData.value = it }
            result.onFailure { handleFailure(it) }
        }
    }
}
