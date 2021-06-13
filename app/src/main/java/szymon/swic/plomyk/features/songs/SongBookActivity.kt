package szymon.swic.plomyk.features.songs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import szymon.swic.plomyk.R
import szymon.swic.plomyk.features.splash.SplashScreenFragment

class SongBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(SplashScreenFragment.newInstance())
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .apply { if (addToBackStack) this.addToBackStack("") }
            .commit()
    }
}