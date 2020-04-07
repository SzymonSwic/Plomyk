package szymon.swic.plomyk.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import szymon.swic.plomyk.R
import szymon.swic.plomyk.factories.Injector
import szymon.swic.plomyk.viewmodel.SongBookVM

class SongBookActivity : AppCompatActivity() {

    private lateinit var songBookVM: SongBookVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        initViewModel()

        addFragment(SongListFragment.newInstance())


//        btn_add.setOnClickListener { addSongTest() }
//        btn_get.setOnClickListener { songBookVM.getAllSongs() }
    }

    private fun initViewModel() {
        val factory = Injector.getSongBookVMFactory()
        songBookVM = ViewModelProvider(this, factory)
            .get(SongBookVM::class.java)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.songlist_fragment, fragment)
            .commit()
    }
}