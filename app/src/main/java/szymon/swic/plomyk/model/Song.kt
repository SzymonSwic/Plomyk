package szymon.swic.plomyk.model


data class Song(val title: String = "No name song",
                val author: String = "No author song",
                val genre: String = "None",
                val lyrics: String = ""){

    override fun toString(): String {
        return "$title - $author"
    }

    fun getSongMap(): Map<String, String> {

        return hashMapOf(
            "title"  to title,
            "author" to author,
            "genre"  to genre,
            "lyrics" to lyrics
        )
    }
}