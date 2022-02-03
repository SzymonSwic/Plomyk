package szymon.swic.plomyk.features.songs.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldContain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import szymon.swic.plomyk.core.database.AppDatabase
import szymon.swic.plomyk.features.songs.data.local.model.SongCached

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MedUsageDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: SongDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.songDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun givenSong_whenDaoSaveSongsCalled_thenGetSongsFromDaoContainSavedSong() {
        runBlockingTest {
            // given
            val song = SongCached(
                id = 123,
                title = "Title",
                author = "Author",
                lyrics = "Lyrics"
            )

            // when
            dao.saveSongs(song)

            // then
            val songs = dao.getSongs()
            songs.shouldContain(song)
        }
    }
}
