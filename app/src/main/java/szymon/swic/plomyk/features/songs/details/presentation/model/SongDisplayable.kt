package szymon.swic.plomyk.features.songs.details.presentation.model

import szymon.swic.plomyk.features.songs.domain.model.Song

data class SongDisplayable(
    val title: String,
    val author: String,
    val lyrics: String
) {
    constructor(song: Song) : this(
        title = song.title,
        author = song.author,
        lyrics = song.lyrics
    )
}
