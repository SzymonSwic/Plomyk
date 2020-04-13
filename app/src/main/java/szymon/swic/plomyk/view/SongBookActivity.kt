package szymon.swic.plomyk.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import szymon.swic.plomyk.R
import szymon.swic.plomyk.factories.Injector
import szymon.swic.plomyk.viewmodel.SongBookVM

class SongBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()

        replaceFragment(SongListFragment.newInstance())
    }

    private fun initViewModel() {
        val factory = Injector.getSongBookVMFactory()
        val viewModel = ViewModelProviders.of(this, factory)
            .get(SongBookVM::class.java)
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }
}