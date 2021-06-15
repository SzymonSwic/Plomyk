package szymon.swic.plomyk.features.splash.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import szymon.swic.plomyk.core.base.BaseViewModel
import szymon.swic.plomyk.core.exception.ErrorMapper
import szymon.swic.plomyk.features.splash.navigation.SplashNavigator

class SplashScreenViewModel(
    private val splashNavigator: SplashNavigator,
    errorMapper: ErrorMapper
) : BaseViewModel(errorMapper) {

    fun onSplashDismissed() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(1500)
            splashNavigator.openSongList()
        }
    }
}
