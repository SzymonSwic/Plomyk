package szymon.swic.plomyk.model

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot


class SongRepository {

    private val TAG = "SongRepository"
    private val SONGS_COLLECTION = "songs"

    private lateinit var db: FirebaseFirestore

    init {
        initDatabase()
    }

    companion object {
        @Volatile
        private var instance: SongRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: SongRepository().also { instance = it }
            }
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

        val queryResult = db.collection(SONGS_COLLECTION).get()

        while (!queryResult.isComplete){
            //add timeout later
        }

        for (document in queryResult.result!!.documents) {
            Log.d(TAG, document.id)
            val newSong: Song = document.toObject(Song::class.java)!!
            songList.add(newSong)
            songList.forEach {
                Log.d(TAG, "XD $it")
            }
        }

        songList.forEach {
            Log.d(TAG, "koniec $it")
        }

        return songList
    }

    fun getAllSongsQuery(): Query {
        return db.collection(SONGS_COLLECTION).orderBy("title", Query.Direction.ASCENDING)
    }
}