package szymon.swic.plomyk.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.songview_fragment.*

import szymon.swic.plomyk.R
import szymon.swic.plomyk.model.Song
import szymon.swic.plomyk.viewmodel.SongViewVM

class SongViewFragment(val song: Song) : Fragment() {

    private val TAG = "SongViewFragment"

    companion object {
        fun newInstance(song: Song) = SongViewFragment(song)
    }

    private lateinit var viewModel: SongViewVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.songview_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SongViewVM::class.java)
        loadSong()
        Log.d(TAG, "Fragment Created")
    }

    private fun loadSong() {
        text_view_author.text = song.author
        text_view_title.text = song.title
        text_view_song_lyrics.text = song.lyrics
        Log.d(TAG, text_view_author.text.toString())
        Log.d(TAG, text_view_title.text.toString())
        Log.d(TAG, text_view_song_lyrics.text.toString())
    }

}
