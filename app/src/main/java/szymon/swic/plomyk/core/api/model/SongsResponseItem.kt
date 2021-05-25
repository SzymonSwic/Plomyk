package szymon.swic.plomyk.core.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SongsResponseItem(
    @SerialName("author") val author: String,
    @SerialName("lyrics") val lyrics: String,
    @SerialName("pub_date") val pubDate: String,
    @SerialName("title") val title: String
)
