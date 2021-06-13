package szymon.swic.plomyk.features.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import szymon.swic.plomyk.R
import szymon.swic.plomyk.features.songs.SongBookActivity
import szymon.swic.plomyk.features.songs.list.presentation.SongListFragment

class SplashScreenFragment : Fragment() {

    companion object {
        fun newInstance() = SplashScreenFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            delay(1500)
            (activity as SongBookActivity).replaceFragmentWithoutBackstack(SongListFragment.newInstance())
        }
    }
}
