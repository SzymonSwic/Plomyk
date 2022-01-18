package szymon.swic.plomyk.features.songs.navigation

import szymon.swic.plomyk.R
import szymon.swic.plomyk.core.navigation.FragmentNavigator
import szymon.swic.plomyk.features.songs.presentation.details.SongDetailsFragment
import szymon.swic.plomyk.features.songs.presentation.details.model.SongDisplayable

class SongNavigatorImpl(
    private val fragmentNavigator: FragmentNavigator
) : SongNavigator {
    override fun openSongDetailScreen(song: SongDisplayable) {
        fragmentNavigator.navigateTo(
            R.id.openSongDetails,
            SongDetailsFragment.SONG_DETAILS_KEY to song
        )
    }

    override fun openTuner() = fragmentNavigator.navigateTo(R.id.openTuner)

    override fun goBack() = fragmentNavigator.goBack()
}
