package szymon.swic.plomyk.model

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class SongRepository {

    private val TAG = "MainActivity"
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
        db.collection(SONGS_COLLECTION).document(song.toString())
            .set(song.getSongHashMap())
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun getAllSongs(){
        db.collection(SONGS_COLLECTION)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }


}