package szymon.swic.plomyk.core.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import szymon.swic.plomyk.core.api.RestApi

const val API_URL = "https://plomyk-songbook-api.herokuapp.com/songbook/data/"

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(OkHttpClient.Builder().build())
            .build()
    }

    single {
        get<Retrofit>().create(RestApi::class.java)
    }
}
