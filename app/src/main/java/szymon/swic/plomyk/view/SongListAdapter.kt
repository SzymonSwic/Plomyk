package szymon.swic.plomyk.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.songlist_item.view.*
import szymon.swic.plomyk.R
import szymon.swic.plomyk.model.Song


class SongListAdapter(options: FirestoreRecyclerOptions<Song>) :
    FirestoreRecyclerAdapter<Song, SongListAdapter.SongHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        return SongHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.songlist_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SongHolder, position: Int, model: Song) {
        holder.bind(model)
    }

    inner class SongHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textTitle: TextView = itemView.text_title
        var textAuthor: TextView = itemView.text_author

        fun bind(song: Song) {
            textTitle.text = song.title
            textAuthor.text = song.author
        }
    }
}