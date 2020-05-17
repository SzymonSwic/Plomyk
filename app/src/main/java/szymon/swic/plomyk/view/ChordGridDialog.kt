package szymon.swic.plomyk.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import szymon.swic.plomyk.R

class ChordGridDialog(private val listOfChordsImagesId: Array<Int>): DialogFragment() {

    private lateinit var chordGridRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ChordGridDialog", "Grid inflated")
        return inflater.inflate(R.layout.chords_dialog, container, true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupChordGridRecyclerView()
    }

    private fun setupChordGridRecyclerView() {
        chordGridRecyclerView = view!!.findViewById(R.id.chords_images_recycler_view)
        chordGridRecyclerView.layoutManager = GridLayoutManager(this.context, 2)
        chordGridRecyclerView.adapter = ChordGridAdapter(listOfChordsImagesId)
        chordGridRecyclerView.setHasFixedSize(true)
    }
}