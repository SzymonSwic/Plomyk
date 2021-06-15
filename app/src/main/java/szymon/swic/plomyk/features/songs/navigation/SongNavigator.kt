package szymon.swic.plomyk.features.songs.navigation

import szymon.swic.plomyk.features.songs.details.presentation.model.SongDisplayable

interface SongNavigator {
    fun openSongDetailScreen(song: SongDisplayable)
    fun openTuner()
    fun goBack()
}
