package szymon.swic.plomyk.features.songs.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.songlist_item.view.*
import szymon.swic.plomyk.R
import szymon.swic.plomyk.features.songs.presentation.details.model.SongDisplayable


class SongListAdapter : RecyclerView.Adapter<SongListAdapter.SongHolder>() {

    private var songList = mutableListOf<SongDisplayable>()
    lateinit var onSongClickListener: (SongDisplayable) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.songlist_item, parent, false)

        return SongHolder(itemView)
    }

    override fun onBindViewHolder(holder: SongHolder, position: Int) =
        holder.bind(songList[position], onSongClickListener)

    override fun getItemCount(): Int = songList.size

    fun setSongs(songs: List<SongDisplayable>) {
        this.songList.clear()
        this.songList.addAll(songs)
        notifyDataSetChanged()
    }

    class SongHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            song: SongDisplayable,
            onSongClicked: (SongDisplayable) -> Unit
        ) = with(itemView) {
            text_title.text = song.title
            text_author.text = song.author
            setOnClickListener { onSongClicked.invoke(song) }
        }
    }
}
