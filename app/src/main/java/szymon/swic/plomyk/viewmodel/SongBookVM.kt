package szymon.swic.plomyk.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import szymon.swic.plomyk.model.Song
import szymon.swic.plomyk.model.SongRepository
import szymon.swic.plomyk.view.SongListAdapter

class SongBookVM(private val songRepository: SongRepository) : ViewModel() {

    private var TestSongCounter = 1

    fun addSong(song: Song) = songRepository.addSong(song)

    fun getAllSongs() = songRepository.getAllSongs()

    private fun getAllSongsQuery() = songRepository.getAllSongsQuery()

    fun initSongListAdapter(onSongListener: SongListAdapter.OnSongListener): SongListAdapter {
        val allSongsQuery = getAllSongsQuery()
        val options =
            FirestoreRecyclerOptions.Builder<Song>().setQuery(allSongsQuery, Song::class.java)
                .build()
        return SongListAdapter(options, onSongListener)
    }


    //methods for testing

    fun addRandomSongs(amount: Int){
        for(i in 1..amount) addMockedSong(TestSongCounter)
        TestSongCounter++
    }

    private fun addMockedSong(diff: Int) {
        val sampleSong = Song(
            title = diff.toString() + "_Bieszczadzkie Anioły_middle length",
            author = "Stare Dobre Małżeństwo",
            genre = "Turystyczna",
            lyrics = "[a]\n" +
                    "Anioły są takie ciche\n" +
                    "[G]\n" +
                    "zwłaszcza te w Bieszczadach\n" +
                    "      [a]                      [t]      [t]         [t]                      [t]\n" +
                    "gdy spotkasz takiego w górach asdfsadfsadfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf\n" +
                    "  [e]\n" +
                    "wiele z nim nie pogadasz\n" +
                    "\n" +
                    "    [C]                [G]\n" +
                    "Najwyżej na ucho ci powie\n" +
                    "     [C]                 [F]\n" +
                    "gdy będzie w dobrym humorze\n" +
                    "       [C]               [G]\n" +
                    "że skrzydła nosi w plecaku\n" +
                    "[a]           [e]        [a]\n" +
                    "nawet przy dobrej pogodzie\n"
//                    "\n" +
//                    "[a]\n" +
//                    "Anioły są całe zielone\n" +
//                    "[G]\n" +
//                    "zwłaszcza te w Bieszczadach\n" +
//                    "      [a]\n" +
//                    "łatwo w trawie się kryją\n" +
//                    "  [e]\n" +
//                    "i w opuszczonych sadach\n" +
//                    "\n" +
//                    "    [C]                [G]\n" +
//                    "W zielone grają ukradkiem\n" +
//                    "     [C]                 [F]\n" +
//                    "nawet karty mają zielone\n" +
//                    "       [C]               [G]\n" +
//                    "zielone mają pojęcie\n" +
//                    "[a]           [e]        [a]\n" +
//                    "a nawet zielony kielonek\n" +
//                    "\n" +
//                    "   [C]          [G]\n" +
//                    "Anioły bieszczadzkie\n" +
//                    "                 [a]\n" +
//                    "bieszczadzkie anioły\n" +
//                    "        [C]\n" +
//                    "dużo w was radości\n" +
//                    "[G]           [a]\n" +
//                    "i dobrej pogody\n" +
//                    "\n" +
//                    "       [C]         [G]\n" +
//                    "Bieszczadzkie anioły\n" +
//                    "              [a]\n" +
//                    "anioły bieszczadzkie\n" +
//                    "        [C]\n" +
//                    "gdy skrzydłem cię dotkną\n" +
//                    "[G]                [a]\n" +
//                    "już jesteś ich bratem\n"
//            lyrics = "a\\nAnioły są takie ciche\\nG\\nzwłaszcza te w Bieszczadach\\n      a\\ngdy spotkasz takiego w górach\\n  e\\nwiele z nim nie pogadasz\\n\\n    C                G\\nNajwyżej na ucho ci powie\\n     C                 F\\ngdy będzie w dobrym humorze\\n       C               G\\nże skrzydła nosi w plecaku\\na           e        a\\nnawet przy dobrej pogodzie\\n\n"
//            lyrics = "a\nAnioły są takie ciche\nG\nzwłaszcza te w Bieszczadach\n      a\ngdy spotkasz takiego w górach\n  e\nwiele z nim nie pogadasz\n\n    C                G\nNajwyżej na ucho ci powie\n     C                 F\ngdy będzie w dobrym humorze\n       C               G\nże skrzydła nosi w plecaku\na           e        a\nnawet przy dobrej pogodzie\n\n"
        )

        addSong(sampleSong)
    }
}