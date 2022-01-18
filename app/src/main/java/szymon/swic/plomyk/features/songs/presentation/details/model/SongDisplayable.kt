package szymon.swic.plomyk.features.songs.presentation.details.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import szymon.swic.plomyk.features.songs.domain.model.Song

@Parcelize
data class SongDisplayable(
    val title: String,
    val author: String,
    val lyrics: String
) : Parcelable {
    constructor(song: Song) : this(
        title = song.title,
        author = song.author,
        lyrics = song.lyrics
    )
}
