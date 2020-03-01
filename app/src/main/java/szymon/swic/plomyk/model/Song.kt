package szymon.swic.plomyk.model


data class Song(val author: String = "Nieznany",
                val genre: String = "Brak",
                val inlineChordLyrics: String){

    override fun toString(): String {
        return "$author - $genre"
    }
}