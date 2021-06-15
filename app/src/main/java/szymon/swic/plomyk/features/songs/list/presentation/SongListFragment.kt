package szymon.swic.plomyk.features.songs.list.presentation

import android.annotation.SuppressLint
import android.view.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.songlist_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import szymon.swic.plomyk.R
import szymon.swic.plomyk.core.base.BaseFragment
import szymon.swic.plomyk.core.extensions.setupSearchView
import szymon.swic.plomyk.features.MainActivity
import szymon.swic.plomyk.features.songs.details.presentation.SongDetailsFragment
import szymon.swic.plomyk.features.songs.details.presentation.model.SongDisplayable
import szymon.swic.plomyk.features.tuner.TunerFragment


class SongListFragment : BaseFragment<SongBookViewModel>(R.layout.songlist_fragment) {

    override val viewModel: SongBookViewModel by viewModel()
    private lateinit var songListAdapter: SongListAdapter

    override fun initViews() {
        super.initViews()
        setupSongListRecyclerView()
        activity?.title = resources.getString(R.string.title_songs)

        buttonTuner.setOnClickListener {
            (activity as MainActivity).replaceFragment(TunerFragment.newInstance(), true)
        }
    }

    override fun initObservers() {
        super.initObservers()
        observeSongs()
    }

    override fun onIdleState() {
        super.onIdleState()
        songlistProgress.isVisible = false
    }

    override fun onPendingState() {
        super.onPendingState()
        songlistProgress.isVisible = true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_bar_menu, menu)

        menu.setupSearchView {
            songListAdapter.setSongs(viewModel.getFilteredSongs(it))
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupSongListRecyclerView() {

        songListAdapter = SongListAdapter()
        songListAdapter.onSongClickListener = ::onSongClick

        songlist_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@SongListFragment.context)
            adapter = songListAdapter
            setHasFixedSize(true)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun observeSongs() {
        viewModel.songs.observe(viewLifecycleOwner) {
            if (!hasOptionsMenu()) setHasOptionsMenu(true)
            songListAdapter.setSongs(it)
        }
    }

    private fun onSongClick(song: SongDisplayable) {
        (activity as MainActivity).replaceFragment(SongDetailsFragment.newInstance(song), true)
    }
}
