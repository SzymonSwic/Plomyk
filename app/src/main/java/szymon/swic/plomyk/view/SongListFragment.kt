package szymon.swic.plomyk.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.songlist_fragment.*
import szymon.swic.plomyk.R
import szymon.swic.plomyk.factories.Injector
import szymon.swic.plomyk.model.Song
import szymon.swic.plomyk.viewmodel.SongBookVM

class SongListFragment : Fragment(), OnSongClickListener {

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
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.songlist_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        setupSongListRecyclerView()

        buttonTuner.setOnClickListener { (activity as SongBookActivity).replaceFragment(TunerTestFragment.newInstance()) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_bar_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
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

    private fun setupViewModel() {
        activity?.let {
            val factory = Injector.getSongBookVMFactory()
            viewModel = ViewModelProviders.of(this, factory)
                .get(SongBookVM::class.java)
        }
    }

    private fun setupSongListRecyclerView() {

        songListAdapter = viewModel.initSongListAdapter(this@SongListFragment)

        songlist_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@SongListFragment.context)
            adapter = songListAdapter
            setHasFixedSize(true)
        }
    }

    override fun onSongClick(target_song: Song) {
        (activity as SongBookActivity).replaceFragment(SongViewFragment.newInstance(target_song))
    }
}