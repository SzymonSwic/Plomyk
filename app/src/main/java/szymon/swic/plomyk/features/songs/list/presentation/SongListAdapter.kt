package szymon.swic.plomyk.features.songs.list.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.songlist_item.view.*
import szymon.swic.plomyk.R
import szymon.swic.plomyk.features.songs.domain.model.Song
import java.util.*


class SongListAdapter(
    private val contentOriginal: MutableList<Song>
) : RecyclerView.Adapter<SongListAdapter.SongHolder>(), Filterable {

    private val TAG = "SongListAdapter"

    private var filterableList = contentOriginal.getCopy()
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

    override fun getFilter(): Filter {
        return songFilter
    }

    private val songFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            filterableList = contentOriginal.getCopy()
            val filteredSongs = mutableListOf<Song>()
            if (constraint == null || constraint.isEmpty()) {
                filteredSongs.addAll(contentOriginal)
            } else {
                val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()

                for (song in contentOriginal) {
                    if (song.title.toLowerCase(Locale.ROOT).contains(filterPattern) ||
                        song.author.toLowerCase(Locale.ROOT).contains(filterPattern)
                    ) {
                        filteredSongs.add(song)
                    }
                }
            }
            val filterResult = FilterResults()
            filterResult.values = filteredSongs

            return filterResult
        }

        override fun publishResults(constraint: CharSequence?, filterResult: FilterResults?) {
            filterableList.clear()
            filterableList.addAll(filterResult?.values as Collection<Song>)
            notifyDataSetChanged()
        }
    }

    fun <T> List<T>.getCopy(): MutableList<T> {
        val original = this
        return mutableListOf<T>().apply { addAll(original) }
    }

    fun setSongs(songs: List<Song>) {
        if (songs.isNotEmpty()) {
            this.filterableList.clear()
        }

        this.filterableList.addAll(songs)
        notifyDataSetChanged()
    }
}
