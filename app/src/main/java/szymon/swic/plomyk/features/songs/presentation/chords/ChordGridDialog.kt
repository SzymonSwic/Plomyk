package szymon.swic.plomyk.features.songs.presentation.chords

import android.os.Bundle
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
    ): View? = inflater.inflate(R.layout.chords_dialog, container, true)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChordGridRecyclerView()
    }

    private fun setupChordGridRecyclerView() {
        chordGridRecyclerView = requireView().findViewById(R.id.chords_images_recycler_view)
        chordGridRecyclerView.layoutManager = GridLayoutManager(this.context, 2)
        chordGridRecyclerView.adapter = ChordGridAdapter(listOfChordsImagesId)
        chordGridRecyclerView.setHasFixedSize(true)
    }
}
