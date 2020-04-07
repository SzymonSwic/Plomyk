package szymon.swic.plomyk.factories

import szymon.swic.plomyk.model.SongRepository
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import szymon.swic.plomyk.model.Song

object Injector {

    fun getSongBookVMFactory(): SongBookVMFactory {
        val songRepository = SongRepository.getInstance()
        return SongBookVMFactory(songRepository)
    }

    fun getFSRecyclerOption(query: Query): FirestoreRecyclerOptions<Song> {
        return FirestoreRecyclerOptions.Builder<Song>().setQuery(query, Song::class.java).build()
    }

}