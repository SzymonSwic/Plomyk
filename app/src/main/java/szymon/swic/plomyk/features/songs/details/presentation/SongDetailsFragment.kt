package szymon.swic.plomyk.features.songs.details.presentation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.songview_fragment.*
import szymon.swic.plomyk.R
import szymon.swic.plomyk.core.base.BaseFragment
import szymon.swic.plomyk.features.songs.domain.model.Song


class SongDetailsFragment(
    val song: Song
) : BaseFragment<SongDetailsViewModel>(R.layout.songview_fragment) {

    private val TAG = "SongViewFragment";

    override val viewModel: SongDetailsViewModel by viewModel()
    private lateinit var animator: ObjectAnimator

    companion object {
        fun newInstance(song: Song) = SongDetailsFragment(song)
    }

    override fun initViews() {
        super.initViews()
        setHasOptionsMenu(true)
        setupView()
        setupAutoscroll()
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

    private fun setupView() {
        activity?.title = song.title

        text_view_song_lyrics.text =
            viewModel.getFormattedSpannableText(song.lyrics, requireContext())

        button_key_up.setOnClickListener {
            Log.d(TAG, "KEY UP")
            text_view_song_lyrics.text = viewModel.getTransposedText(1, requireContext())
        }

        button_key_down.setOnClickListener {
            Log.d(TAG, "KEY DOWN")
            text_view_song_lyrics.text = viewModel.getTransposedText(-1, requireContext())
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
        val dialog = viewModel.getChordsDialog(requireActivity().applicationContext)
        dialog.show(requireFragmentManager(), "chords_dialog")
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
        if (!this::animator.isInitialized) {
            animator = ObjectAnimator
                .ofInt(song_scrollview, "scrollY", text_view_song_lyrics.bottom)
                .setDuration(viewModel.getAnimationDuration(text_view_song_lyrics))
        }
        return animator
    }
}
