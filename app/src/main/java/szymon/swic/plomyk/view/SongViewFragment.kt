package szymon.swic.plomyk.view

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.*
import android.widget.ScrollView
import android.widget.TextView
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
        viewModel = ViewModelProviders.of(this).get(SongViewVM::class.java)
        setupView()
        setupAutoscroll()

        Log.d(TAG, "SongView Fragment Created")
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.songview_menu, menu)
    }

    private fun setupView() {
        text_view_author.text = song.author
        text_view_title.text = song.title
        text_view_song_lyrics.text = viewModel.getFormattedSpannableText(song.lyrics, text_view_song_lyrics)
    }

    private fun setupAutoscroll() {
        val songScrollView: ScrollView = view!!.findViewById(R.id.song_scrollview)


        text_view_song_lyrics.setOnLongClickListener {
            Log.d(TAG, "Long Click Event")

            animator = ObjectAnimator.ofInt(songScrollView, "scrollY", text_view_song_lyrics.bottom).setDuration(getAnimationDuration(text_view_song_lyrics))
            animator.start()

            true
        }

        text_view_song_lyrics.setOnClickListener {
            if(animator.isRunning) {
                animator.cancel()
            }
        }
    }

    private fun getAnimationDuration(textView: TextView): Long {
        return (textView.lineCount*700).toLong()
    }

}
