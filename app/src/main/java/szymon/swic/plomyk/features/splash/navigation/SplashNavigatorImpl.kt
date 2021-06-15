package szymon.swic.plomyk.features.splash.navigation

import szymon.swic.plomyk.R
import szymon.swic.plomyk.core.navigation.FragmentNavigator

class SplashNavigatorImpl(
    private val fragmentNavigator: FragmentNavigator
) : SplashNavigator {
    override fun openSongList() = fragmentNavigator.navigateTo(R.id.openSongList)
}
