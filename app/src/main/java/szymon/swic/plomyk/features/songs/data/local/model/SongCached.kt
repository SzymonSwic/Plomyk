package szymon.swic.plomyk.features.songs.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import szymon.swic.plomyk.features.songs.domain.model.Song

@Entity(tableName = "songs")
data class SongCached(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val author: String,
    val lyrics: String
) {
    constructor(song: Song) : this(
        id = 0,
        title = song.title,
        author = song.author,
        lyrics = song.lyrics
    )

    fun toSong() = Song(
        title = title,
        author = author,
        lyrics = lyrics
    )
}
