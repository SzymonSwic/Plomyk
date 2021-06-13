package szymon.swic.plomyk.features.songs.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import szymon.swic.plomyk.core.api.RestApi
import szymon.swic.plomyk.core.exception.ErrorWrapperImpl
import szymon.swic.plomyk.core.exception.callOrThrow
import szymon.swic.plomyk.features.songs.domain.model.Song


class SongRepository(
    private val restApi: RestApi
) {

    private val TAG = "SongRepository"
    private val SONGS_COLLECTION = "songs"

    private lateinit var db: FirebaseFirestore

    init {
        initDatabase()
    }

    private fun initDatabase() {
        db = FirebaseFirestore.getInstance()

        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
            .build()

        db.firestoreSettings = settings
    }

    fun addSong(song: Song) {
        db.collection(SONGS_COLLECTION)
            .add(song.getSongMap())
            .addOnSuccessListener { docRef ->
                Log.d(
                    TAG,
                    "DocumentSnapshot successfully written with ID: $docRef"
                )
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun getAllSongs(): MutableList<Song> {

        var songList: MutableList<Song> = ArrayList()

        val queryResult = db.collection(SONGS_COLLECTION).orderBy("title", Query.Direction.ASCENDING).get()

        while (!queryResult.isComplete){}

        for (document in queryResult.result!!.documents) {
            Log.d(TAG, document.id)
            val newSong: Song = document.toObject(Song::class.java)!!
            songList.add(newSong)
        }

        return songList
    }

    suspend fun getSongsFromApi(): List<Song> {
        val wrapper = ErrorWrapperImpl()

        return callOrThrow(wrapper) {
            restApi.getSongs().map { Song(it.title, it.author, it.lyrics) }
        }
    }

    fun getAllSongsQuery(): Query {
        return db.collection(SONGS_COLLECTION).orderBy("title", Query.Direction.ASCENDING)
    }
}