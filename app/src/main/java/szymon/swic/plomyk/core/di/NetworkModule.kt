package szymon.swic.plomyk.core.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import szymon.swic.plomyk.core.api.RestApi
import java.util.concurrent.TimeUnit

private const val API_URL = "https://plomyk-songbook-api.herokuapp.com/songbook/data/"
private const val API_TIMEOUT = 10L

val networkModule = module {
    single {
        Json.asConverterFactory("application/json".toMediaType())
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(get())
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(RestApi::class.java)
    }
}
