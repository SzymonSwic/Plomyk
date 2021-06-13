package szymon.swic.plomyk.core.factories

import szymon.swic.plomyk.features.songs.data.repository.SongRepository
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import szymon.swic.plomyk.core.api.RestApi
import szymon.swic.plomyk.features.songs.domain.model.Song

object Injector {

    fun getSongBookVMFactory(): SongBookVMFactory {
        val songRepository = SongRepository.getInstance()
        return SongBookVMFactory(songRepository)
    }

    fun getFSRecyclerOption(query: Query): FirestoreRecyclerOptions<Song> {
        return FirestoreRecyclerOptions.Builder<Song>().setQuery(query, Song::class.java).build()
    }

    fun getRetrofit() = Retrofit.Builder()
        .baseUrl("https://plomyk-songbook-api.herokuapp.com/songbook/data/")
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(OkHttpClient.Builder().build())
        .build()
        .create(RestApi::class.java)
}
