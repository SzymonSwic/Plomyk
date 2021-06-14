package szymon.swic.plomyk.core.database

import androidx.room.TypeConverter
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import szymon.swic.plomyk.features.songs.data.local.model.SongCached
import szymon.swic.plomyk.features.songs.domain.model.Song

class TypeConverters {

    companion object {

        @TypeConverter
        @JvmStatic
        fun songCachedListToString(value: List<SongCached>): String = Json.encodeToString(value)

        @TypeConverter
        @JvmStatic
        fun songCachedListFromString(value: String): List<SongCached> = Json.decodeFromString(value)

        @TypeConverter
        @JvmStatic
        fun songCachedToString(value: SongCached): String = Json.encodeToString(value)

        @TypeConverter
        @JvmStatic
        fun songCachedFromString(value: String): SongCached = Json.decodeFromString(value)
    }
}
