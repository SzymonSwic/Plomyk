package szymon.swic.plomyk.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.songlist_fragment.*
import szymon.swic.plomyk.R
import szymon.swic.plomyk.factories.Injector
import szymon.swic.plomyk.viewmodel.SongBookVM

class SongListFragment : Fragment() {

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
        initViewModel()
        initSongListRecyclerView()
    }

    private fun initViewModel() {
        val factory = Injector.getSongBookVMFactory()
        viewModel = ViewModelProvider(this, factory)
            .get(SongBookVM::class.java)
    }

    private fun initSongListRecyclerView() {
        songListRecyclerView = view!!.findViewById(R.id.songlist_recycler_view)
        songListRecyclerView.layoutManager = LinearLayoutManager(this@SongListFragment.context)
        songListRecyclerView.adapter = viewModel.initSongListAdapter()
    }

}
