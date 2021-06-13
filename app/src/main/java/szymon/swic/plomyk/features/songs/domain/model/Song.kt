package szymon.swic.plomyk.features.songs.domain.model


data class Song(val title: String = "No name song",
                val author: String = "No author song",
                val lyrics: String = ""){

    override fun toString(): String {
        return "$title - $author"
    }

    fun getSongMap(): Map<String, String> {
        return hashMapOf(
            "title"  to title,
            "author" to author,
            "lyrics" to lyrics
        )
    }
}