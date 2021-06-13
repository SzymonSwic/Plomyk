package szymon.swic.plomyk.features.songs.details.presentation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.songview_fragment.*
import szymon.swic.plomyk.R
import szymon.swic.plomyk.features.songs.domain.model.Song


class SongDetailsFragment(val song: Song) : Fragment() {

    private val TAG = "SongViewFragment";

    companion object {
        fun newInstance(song: Song) = SongDetailsFragment(song)
    }

    private lateinit var viewModel: SongDetailsViewModel
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
            R.id.menu_key_change -> {
                showKeyChangeButtons()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewModel() {
        activity?.let {
            viewModel = ViewModelProviders.of(it)
                .get(SongDetailsViewModel::class.java)
        }
    }

    private fun setupView() {
        activity?.title = song.title

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

        button_hide_key_change.setOnClickListener {
            hideKeyChangeButtons()
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

    private fun showKeyChangeButtons() {
        button_key_up.visibility = View.VISIBLE
        button_key_down.visibility = View.VISIBLE
        button_hide_key_change.visibility = View.VISIBLE
    }

    private fun hideKeyChangeButtons() {
        button_key_up.visibility = View.GONE
        button_key_down.visibility = View.GONE
        button_hide_key_change.visibility = View.GONE
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
