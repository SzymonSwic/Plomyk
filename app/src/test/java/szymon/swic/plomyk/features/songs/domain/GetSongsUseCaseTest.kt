package szymon.swic.plomyk.features.songs.domain

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.GlobalScope
import org.junit.jupiter.api.Test

internal class GetSongsUseCaseTest {

    @Test
    fun `WHEN use case is invoked THEN getSongs method from repository is executed`() {
        // given
        val repository = mockk<SongRepository>(relaxed = true)
        val useCase = GetSongsUseCase(repository)

        // when
        useCase(
            params = Unit,
            scope = GlobalScope
        )

        // then
        coVerify { repository.getSongs() }
    }
}
