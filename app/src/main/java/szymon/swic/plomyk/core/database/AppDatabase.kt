package szymon.swic.plomyk.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import szymon.swic.plomyk.features.songs.data.local.SongDao
import szymon.swic.plomyk.features.songs.data.local.model.SongCached

@Database(
    entities = [SongCached::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun songDao(): SongDao
}
