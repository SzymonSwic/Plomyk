package szymon.swic.plomyk.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.songlist_item.view.*
import szymon.swic.plomyk.R
import szymon.swic.plomyk.model.Song
import java.util.*


class SongListAdapter(
    private var content: MutableList<Song>,
    private val onSongClickListener: OnSongListener
) : RecyclerView.Adapter<SongListAdapter.SongHolder>(), Filterable {

    private val TAG = "SongListAdapter"

    private val filterableList = content

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        Log.d(TAG, "ViewHolder Created")
        return SongHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.songlist_item, parent, false),
            onSongClickListener
        )
    }

    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        holder.bind(content[position])
        Log.d(TAG, "Song Binded")
    }

    override fun getItemCount(): Int = content.size

    inner class SongHolder constructor(
        itemView: View,
        private var songClickListener: OnSongListener
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        lateinit var target_song: Song

        fun bind(song: Song) {
            itemView.text_title.text = song.title
            itemView.text_author.text = song.author
            target_song = song
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            songClickListener.onSongClick(target_song)
        }
    }

    override fun getFilter(): Filter {
        return songFilter
    }

    val songFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredSongs = mutableListOf<Song>()
            if (constraint == null || constraint.isEmpty()) {
                filteredSongs.addAll(filterableList)
            } else {
                val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()

                for (song in filterableList) {
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
            content.clear()
            content.addAll(filterResult?.values as Collection<Song>)
            notifyDataSetChanged()
        }
    }
}