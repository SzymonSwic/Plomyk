package szymon.swic.plomyk.features.songs.presentation.list

import android.annotation.SuppressLint
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import szymon.swic.plomyk.R
import szymon.swic.plomyk.core.base.BaseFragment
import szymon.swic.plomyk.core.extensions.setupSearchView
import szymon.swic.plomyk.core.utils.viewBinding
import szymon.swic.plomyk.databinding.FragmentSongsListBinding


class SongListFragment : BaseFragment<SongBookViewModel>(R.layout.fragment_songs_list) {

    private val binding by viewBinding<FragmentSongsListBinding>()
    override val viewModel: SongBookViewModel by viewModel()
    private val songListAdapter by inject<SongListAdapter>()

    override fun initViews() {
        super.initViews()
        setupSongListRecyclerView()
        activity?.title = resources.getString(R.string.title_songs)

        binding.buttonTuner.setOnClickListener {
            viewModel.onOpenTunerClicked()
        }

        setupBack()
    }

    override fun initObservers() {
        super.initObservers()
        observeSongs()
    }

    override fun onIdleState() {
        super.onIdleState()
        binding.songlistProgress.isVisible = false
    }

    override fun onPendingState() {
        super.onPendingState()
        binding.songlistProgress.isVisible = true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_bar_menu, menu)

        menu.setupSearchView {
            songListAdapter.setSongs(viewModel.getFilteredSongs(it))
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupSongListRecyclerView() {
        songListAdapter.onSongClickListener = viewModel::onSongClicked

        binding.songlistRecyclerView.apply {
            layoutManager = get<LinearLayoutManager>()
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

    //TODO: temporary solution
    private fun setupBack() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}
