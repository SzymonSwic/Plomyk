package szymon.swic.plomyk.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.songlist_fragment.*
import szymon.swic.plomyk.R
import szymon.swic.plomyk.model.Song
import szymon.swic.plomyk.viewmodel.SongBookVM

class SongListFragment : Fragment(), SongListAdapter.OnSongListener {

    private val TAG = "SongListFragment"

    private lateinit var viewModel: SongBookVM
    private lateinit var songListAdapter: SongListAdapter
    private lateinit var songListRecyclerView: RecyclerView

    companion object {
        fun newInstance() = SongListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.songlist_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        setupSongListRecyclerView()

        button.setOnClickListener { viewModel.addRandomSongs(1) }
    }

    override fun onStart() {
        super.onStart()
        songListAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        songListAdapter.stopListening()
    }

    private fun setupViewModel() {
        activity?.let {
            viewModel = ViewModelProviders.of(it)
                .get(SongBookVM::class.java)
        }
    }

    private fun setupSongListRecyclerView() {
        songListAdapter = viewModel.initSongListAdapter(this)

        songListRecyclerView = view!!.findViewById(R.id.songlist_recycler_view)
        songListRecyclerView.layoutManager = LinearLayoutManager(this@SongListFragment.context)
        songListRecyclerView.adapter = songListAdapter
        songListRecyclerView.setHasFixedSize(true)
    }

    override fun onSongClick(target_song: Song) {
        (activity as SongBookActivity).replaceFragment(SongViewFragment.newInstance(target_song))
    }
}