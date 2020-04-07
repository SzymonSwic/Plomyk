package szymon.swic.plomyk.viewmodel

import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import szymon.swic.plomyk.model.Song
import szymon.swic.plomyk.model.SongRepository
import szymon.swic.plomyk.view.SongListAdapter

class SongBookVM(private val songRepository: SongRepository) : ViewModel() {

    fun addSong(song: Song) = songRepository.addSong(song)

    fun getAllSongs() = songRepository.getAllSongs()

    private fun getAllSongsQuery() = songRepository.getAllSongsQuery()

    fun addMockedSongs(diff: Int) {
        val sampleSong = Song(
            title = diff.toString()+"_Bieszczadzkie Anioły",
            author = "Stare Dobre Małżeństwo",
            genre = "Turystyczna",
            inlineChordLyrics = "a\\nAnioły są takie ciche\\nG\\nzwłaszcza te w Bieszczadach\\n      a\\ngdy spotkasz takiego w górach\\n  e\\nwiele z nim nie pogadasz\\n\\n    C                G\\nNajwyżej na ucho ci powie\\n     C                 F\\ngdy będzie w dobrym humorze\\n       C               G\\nże skrzydła nosi w plecaku\\na           e        a\\nnawet przy dobrej pogodzie\\n\n")

        addSong(sampleSong)
    }

    fun initSongListAdapter(): SongListAdapter {
        val allSongsQuery = getAllSongsQuery()
        val options = FirestoreRecyclerOptions.Builder<Song>().setQuery(allSongsQuery, Song::class.java).build()

        return SongListAdapter(options)
    }


}