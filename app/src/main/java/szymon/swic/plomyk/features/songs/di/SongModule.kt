package szymon.swic.plomyk.features.songs.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import szymon.swic.plomyk.features.songs.data.repository.SongRepositoryImpl
import szymon.swic.plomyk.features.songs.details.presentation.SongDetailsViewModel
import szymon.swic.plomyk.features.songs.domain.GetSongsUseCase
import szymon.swic.plomyk.features.songs.list.presentation.SongBookViewModel

val songModule = module {

    // data
    factory { SongRepositoryImpl(get(), get(), get()) }

    // domain
    factory { GetSongsUseCase(get()) }

    // presentation
    viewModel { SongBookViewModel(get(), get()) }
    viewModel { SongDetailsViewModel() }
}