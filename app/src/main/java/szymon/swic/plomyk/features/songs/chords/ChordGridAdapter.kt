package szymon.swic.plomyk.features.songs.chords

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.chord_grid_item.view.*
import szymon.swic.plomyk.R

class ChordGridAdapter(private val listOfChordsImagesId: Array<Int>): RecyclerView.Adapter<ChordGridAdapter.ChordImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChordImageViewHolder {
        return ChordImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chord_grid_item, parent, false))
    }

    override fun getItemCount(): Int = listOfChordsImagesId.size

    override fun onBindViewHolder(holder: ChordImageViewHolder, position: Int) {
        holder.bindView(listOfChordsImagesId[position])
        Log.d("ChordAdapter", "Chord Binded")
    }

    inner class ChordImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(imageId: Int) {
            itemView.chord_image.setImageResource(imageId)
        }
    }
}