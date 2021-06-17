package szymon.swic.plomyk.features.tuner.navigation

import szymon.swic.plomyk.R
import szymon.swic.plomyk.core.navigation.FragmentNavigator

class TunerNavigatorImpl(
    private val fragmentNavigator: FragmentNavigator
) : TunerNavigator {
    override fun goBack() {
        fragmentNavigator.clearHistory()
        fragmentNavigator.goBack(R.id.songListFragment)
    }
}
