package szymon.swic.plomyk.features.songs.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import szymon.swic.plomyk.databinding.SonglistItemBinding
import szymon.swic.plomyk.features.songs.presentation.details.model.SongDisplayable


class SongListAdapter : RecyclerView.Adapter<SongListAdapter.SongHolder>() {

    private var songList = mutableListOf<SongDisplayable>()
    lateinit var onSongClickListener: (SongDisplayable) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = SonglistItemBinding.inflate(inflater, parent, false)

        return SongHolder(view)
    }

    override fun onBindViewHolder(holder: SongHolder, position: Int) =
        holder.bind(songList[position], onSongClickListener)

    override fun getItemCount(): Int = songList.size

    fun setSongs(songs: List<SongDisplayable>) {
        this.songList.clear()
        this.songList.addAll(songs)
        notifyDataSetChanged()
    }

    class SongHolder(private val binding: SonglistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            song: SongDisplayable,
            onSongClicked: (SongDisplayable) -> Unit
        ) {
            binding.textTitle.text = song.title
            binding.textAuthor.text = song.author
            binding.root.setOnClickListener { onSongClicked.invoke(song) }
        }
    }
}
