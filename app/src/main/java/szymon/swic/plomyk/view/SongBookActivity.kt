package szymon.swic.plomyk.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import szymon.swic.plomyk.R
import szymon.swic.plomyk.factories.FactoryInjector
import szymon.swic.plomyk.viewmodel.SongBookVM

class SongBookActivity : AppCompatActivity() {

    private lateinit var songBookVM: SongBookVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
//        addSongTest()
        button.setOnClickListener { songBookVM.getAllSongs() }
    }

    private fun initViewModel(){
        val factory = FactoryInjector.getSongBookVMFactory()
        songBookVM = ViewModelProvider(this, factory)
                        .get(SongBookVM::class.java)
    }

    private fun addSongTest(){
        songBookVM.addMockedSongs()
    }
}
