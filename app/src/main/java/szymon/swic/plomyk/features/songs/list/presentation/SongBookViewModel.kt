package szymon.swic.plomyk.features.songs.list.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import szymon.swic.plomyk.core.exception.ErrorMapper
import szymon.swic.plomyk.core.base.BaseViewModel
import szymon.swic.plomyk.features.songs.domain.GetSongsUseCase
import szymon.swic.plomyk.features.songs.domain.model.Song

class SongBookViewModel(
    private val getSongsUseCase: GetSongsUseCase,
    errorMapper: ErrorMapper
) : BaseViewModel(errorMapper) {

    private val TAG = "SongBookVM"
    private var TestSongCounter = 1

    private val _songs by lazy {
        MutableLiveData<List<Song>>()
            .also { getSongs(it) }
    }

    val songs by lazy {
        _songs.map { songs ->
            songs.map { it }
        }
    }

//    fun addSong(song: Song) = songRepository.addSong(song)

//    fun getAllSongs(): MutableList<Song> = songRepository.getAllSongs()

//    private fun getAllSongsQuery() = songRepository.getAllSongsQuery()

    fun initSongListAdapter(onSongClickListener: OnSongClickListener): SongListAdapter {
        Log.d(TAG, "adapter init")
//        val allSongs = getAllSongs()
        return SongListAdapter(mutableListOf(), onSongClickListener)
    }


    //methods for testing

//    fun addMockedSong() {
//        val sampleLyrics = "[a]\n" +
//                "Anioły są takie ciche\n" +
//                "[G]\n" +
//                "zwłaszcza te w Bieszczadach\n" +
//                "      [a]\n" +
//                "gdy spotkasz takiego w górach\n" +
//                "  [e]\n" +
//                "wiele z nim nie pogadasz\n" +
//                "\n" +
//                "    [C]                [G]\n" +
//                "Najwyżej na ucho ci powie\n" +
//                "     [C]                 [F]\n" +
//                "gdy będzie w dobrym humorze\n" +
//                "       [C]               [G]\n" +
//                "że skrzydła nosi w plecaku\n" +
//                "[a]           [e]        [a]\n" +
//                "nawet przy dobrej pogodzie\n" +
//                "\n" +
//                "[a]\n" +
//                "Anioły są całe zielone\n" +
//                "[G]\n" +
//                "zwłaszcza te w Bieszczadach\n" +
//                "      [a]\n" +
//                "łatwo w trawie się kryją\n" +
//                "  [e]\n" +
//                "i w opuszczonych sadach\n" +
//                "\n" +
//                "    [C]                [G]\n" +
//                "W zielone grają ukradkiem\n" +
//                "     [C]                 [F]\n" +
//                "nawet karty mają zielone\n" +
//                "       [C]               [G]\n" +
//                "zielone mają pojęcie\n" +
//                "[a]           [e]        [a]\n" +
//                "a nawet zielony kielonek\n" +
//                "\n" +
//                "   [C]          [G]\n" +
//                "Anioły bieszczadzkie\n" +
//                "                 [a]\n" +
//                "bieszczadzkie anioły\n" +
//                "        [C]\n" +
//                "dużo w was radości\n" +
//                "[G]           [a]\n" +
//                "i dobrej pogody\n" +
//                "\n" +
//                "       [C]         [G]\n" +
//                "Bieszczadzkie anioły\n" +
//                "              [a]\n" +
//                "anioły bieszczadzkie\n" +
//                "        [C]\n" +
//                "gdy skrzydłem cię dotkną\n" +
//                "[G]                [a]\n" +
//                "już jesteś ich bratem\n"
//
//        val sampleTitle = "Piosenka_"
//        val sampleAuthor = "Autor_"
//
//        addSong(anioly)
//        addSong(wind)
//
//        for (i in 0..10) {
//            addSong(
//                Song(
//                    title = "$sampleTitle$i",
//                    author = "$sampleAuthor$i",
//                    lyrics = sampleLyrics
//                )
//            )
//        }
//    }

    private fun getSongs(songsLiveData: MutableLiveData<List<Song>>) {
        getSongsUseCase.invoke(
            params = Unit,
            scope = viewModelScope
        ) { result ->
            result.onSuccess { songsLiveData.value = it }
            result.onFailure { handleFailure(it) }
        }
    }
}
