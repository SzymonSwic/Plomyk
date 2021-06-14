package szymon.swic.plomyk.features.songs.data.local

import androidx.room.*
import szymon.swic.plomyk.features.songs.data.local.model.SongCached

@Dao
interface SongDao {
    @Query("SELECT * FROM songs")
    suspend fun getSongs(): List<SongCached>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSongs(vararg song: SongCached)

    @Query("DELETE FROM songs")
    suspend fun deleteAll()
}
