package szymon.swic.plomyk.features.songs.presentation.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import szymon.swic.plomyk.core.base.BaseViewModel
import szymon.swic.plomyk.core.exception.ErrorMapper
import szymon.swic.plomyk.features.songs.presentation.details.model.SongDisplayable
import szymon.swic.plomyk.features.songs.domain.GetSongsUseCase
import szymon.swic.plomyk.features.songs.domain.model.Song
import szymon.swic.plomyk.features.songs.navigation.SongNavigator

class SongBookViewModel(
    private val getSongsUseCase: GetSongsUseCase,
    private val songNavigator: SongNavigator,
    errorMapper: ErrorMapper
) : BaseViewModel(errorMapper) {

    private val _songs by lazy {
        MutableLiveData<List<Song>>()
            .also { getSongs(it) }
    }

    val songs by lazy {
        _songs.map { songs ->
            songs.map { SongDisplayable(it) }
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

    fun onSongClicked(song: SongDisplayable) = songNavigator.openSongDetailScreen(song)

    fun onOpenTunerClicked() = songNavigator.openTuner()
}
