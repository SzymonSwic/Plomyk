package szymon.swic.plomyk.view

import android.animation.ObjectAnimator
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.songview_fragment.*
import szymon.swic.plomyk.R
import szymon.swic.plomyk.model.Song
import szymon.swic.plomyk.viewmodel.SongViewVM


class SongViewFragment(val song: Song) : Fragment() {

    private val TAG = "SongViewFragment";

    companion object {
        fun newInstance(song: Song) = SongViewFragment(song)
    }

    private lateinit var viewModel: SongViewVM
    private lateinit var animator: ObjectAnimator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.songview_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        setupView()

        setupAutoscroll()

        Log.d(TAG, "SongView Fragment Created")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.songview_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_chords -> {
                showChordsDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewModel() {
        activity?.let {
            viewModel = ViewModelProviders.of(it)
                .get(SongViewVM::class.java)
        }
    }

    private fun setupView() {
        text_view_author.text = song.author
        text_view_title.text = song.title
        text_view_song_lyrics.text =
            viewModel.getFormattedSpannableText(song.lyrics, context!!)

        button_key_up.setOnClickListener {
            Log.d(TAG, "KEY UP")
            text_view_song_lyrics.text = viewModel.getTransposedText(1, context!!)
        }

        button_key_down.setOnClickListener {
            Log.d(TAG, "KEY DOWN")
            text_view_song_lyrics.text = viewModel.getTransposedText(-1, context!!)
        }
}

    private fun setupAutoscroll() {

        text_view_song_lyrics.setOnLongClickListener {
            Log.d(TAG, "Long Click Event")
            getTextAnimator().start()
            true
        }

        text_view_song_lyrics.setOnClickListener {
            Log.d(TAG, "On Click Event")
            if (getTextAnimator().isRunning) {
                getTextAnimator().cancel()
            }
        }
    }

    private fun showChordsDialog() {
        val dialog = viewModel.getChordsDialog(activity!!.applicationContext)
        dialog.show(fragmentManager!!, "chords_dialog")
    }

    private fun getTextAnimator(): ObjectAnimator {
        if(!this::animator.isInitialized) {
            animator = ObjectAnimator
                .ofInt(song_scrollview, "scrollY", text_view_song_lyrics.bottom)
                .setDuration(viewModel.getAnimationDuration(text_view_song_lyrics))
        }
        return animator
    }
}
