package szymon.swic.plomyk.features.songs.list.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.songlist_item.view.*
import szymon.swic.plomyk.R
import szymon.swic.plomyk.features.songs.domain.model.Song


class SongListAdapter : RecyclerView.Adapter<SongListAdapter.SongHolder>() {

    private val TAG = "SongListAdapter"

    private var filterableList = mutableListOf<Song>()
    lateinit var onSongClickListener: (Song) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.songlist_item, parent, false)

        return SongHolder(itemView)
    }

    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        holder.bind(filterableList[position], onSongClickListener)
        Log.d(TAG, "Song Binded")
    }

    override fun getItemCount(): Int = filterableList.size

    inner class SongHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(song: Song, onSongClicked: (Song) -> Unit) = with(itemView) {
            text_title.text = song.title
            text_author.text = song.author
            setOnClickListener { onSongClicked.invoke(song) }
        }
    }

    fun setSongs(songs: List<Song>) {
        this.filterableList.clear()
        this.filterableList.addAll(songs)
        notifyDataSetChanged()
    }
}
