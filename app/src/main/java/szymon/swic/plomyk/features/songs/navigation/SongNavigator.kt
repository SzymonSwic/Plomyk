package szymon.swic.plomyk.features.songs.navigation

import szymon.swic.plomyk.features.songs.presentation.details.model.SongDisplayable

interface SongNavigator {
    fun openSongDetailScreen(song: SongDisplayable)
    fun openTuner()
    fun goBack()
}
