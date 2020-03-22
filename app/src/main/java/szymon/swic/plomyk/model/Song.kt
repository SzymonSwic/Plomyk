package szymon.swic.plomyk.model


data class Song(val title: String,
                val author: String,
                val genre: String = "Brak",
                val inlineChordLyrics: String){

    override fun toString(): String {
        return "$title - $author"
    }

    fun getSongHashMap(): HashMap<String, String> {

        return hashMapOf(
            "title" to title,
            "author" to author,
            "genre" to genre,
            "lyrics" to inlineChordLyrics
        )
    }
}