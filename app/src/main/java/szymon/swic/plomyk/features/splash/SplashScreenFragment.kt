package szymon.swic.plomyk.features.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import szymon.swic.plomyk.R
import szymon.swic.plomyk.features.MainActivity
import szymon.swic.plomyk.features.songs.list.presentation.SongListFragment

class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            delay(1500)
            (activity as MainActivity).replaceFragment(get<SongListFragment>())
        }
    }
}
