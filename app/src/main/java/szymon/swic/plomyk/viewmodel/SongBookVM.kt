package szymon.swic.plomyk.viewmodel

import androidx.lifecycle.ViewModel
import szymon.swic.plomyk.model.Song
import szymon.swic.plomyk.model.SongRepository

class SongBookVM(private val songRepository: SongRepository) : ViewModel() {

    fun addSong(song: Song) = songRepository.addSong(song)

    fun getAllSongs() = songRepository.getAllSongs()


    fun addMockedSongs() {
        val sampleSong = Song(
            title = "Bieszczadzkie Anioły",
            author = "Stare Dobre Małżeństwo",
            genre = "turystyczna",
            inlineChordLyrics = "a\\nAnioły są takie ciche\\nG\\nzwłaszcza te w Bieszczadach\\n      a\\ngdy spotkasz takiego w górach\\n  e\\nwiele z nim nie pogadasz\\n\\n    C                G\\nNajwyżej na ucho ci powie\\n     C                 F\\ngdy będzie w dobrym humorze\\n       C               G\\nże skrzydła nosi w plecaku\\na           e        a\\nnawet przy dobrej pogodzie\\n\n")

        addSong(sampleSong)

    }
}