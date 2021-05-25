package szymon.swic.plomyk.core.api

import retrofit2.http.GET
import szymon.swic.plomyk.core.api.model.SongsResponseItem

interface RestApi {

    @GET("song")
    suspend fun getSongs(): List<SongsResponseItem>
}
