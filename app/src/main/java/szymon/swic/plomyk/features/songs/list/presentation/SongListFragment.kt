package szymon.swic.plomyk.features.songs.list.presentation

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.songlist_fragment.*
import szymon.swic.plomyk.R
import szymon.swic.plomyk.features.songs.SongBookActivity
import szymon.swic.plomyk.features.songs.details.presentation.SongDetailsFragment
import szymon.swic.plomyk.features.songs.domain.model.Song
import szymon.swic.plomyk.features.tuner.TunerFragment

class SongListFragment : Fragment(), OnSongClickListener {

    private val TAG = "SongListFragment"

    private val viewModel: SongBookViewModel by viewModel()
    private lateinit var songListAdapter: SongListAdapter
    private lateinit var songListRecyclerView: RecyclerView

    companion object {
        fun newInstance() = SongListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.songlist_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSongListRecyclerView()
        activity?.title = ""

        buttonTuner.setOnClickListener {
            (activity as SongBookActivity).replaceFragment(TunerFragment.newInstance())
        }

        viewModel.songs.observe(viewLifecycleOwner) {
            songListAdapter.setSongs(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_bar_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newQueryText: String?): Boolean {
                songListAdapter.filter.filter(newQueryText)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupSongListRecyclerView() {

        songListAdapter = viewModel.initSongListAdapter(this@SongListFragment)

        songlist_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@SongListFragment.context)
            adapter = songListAdapter
            setHasFixedSize(true)
        }

        viewModel.songs.observe(this) {
            songListAdapter.setSongs(it)
        }
    }

    override fun onSongClick(target_song: Song) {
        (activity as SongBookActivity).replaceFragment(SongDetailsFragment.newInstance(target_song))
    }
}
