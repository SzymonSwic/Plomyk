package szymon.swic.plomyk.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import szymon.swic.plomyk.features.songs.data.local.SongDao
import szymon.swic.plomyk.features.songs.data.local.model.SongCached

@Database(
    entities = [SongCached::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun songDao(): SongDao
}
