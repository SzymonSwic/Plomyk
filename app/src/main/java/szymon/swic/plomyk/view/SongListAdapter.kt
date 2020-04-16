package szymon.swic.plomyk.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.songlist_item.view.*
import szymon.swic.plomyk.R
import szymon.swic.plomyk.model.Song


class SongListAdapter(options: FirestoreRecyclerOptions<Song>, onSongListener: OnSongListener) :
    FirestoreRecyclerAdapter<Song, SongListAdapter.SongHolder>(options) {

    private val TAG = "SongListAdapter"

    var onSongClickListener = onSongListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        return SongHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.songlist_item, parent, false),
            onSongClickListener
        )
    }

    override fun onBindViewHolder(holder: SongHolder, position: Int, model: Song) {
        holder.bind(model)
    }

    inner class SongHolder constructor(itemView: View, songListener: OnSongListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        lateinit var target_song: Song
        var onSongListener: OnSongListener = songListener

        fun bind(song: Song) {
            itemView.text_title.text = song.title
            itemView.text_author.text = song.author
            target_song = song
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onSongListener.onSongClick(target_song)
        }
    }

    interface OnSongListener {
        fun onSongClick(target_song: Song)
    }
}