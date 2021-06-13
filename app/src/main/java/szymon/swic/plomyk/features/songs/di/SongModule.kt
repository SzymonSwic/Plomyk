package szymon.swic.plomyk.features.songs.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import szymon.swic.plomyk.features.songs.data.repository.SongRepository
import szymon.swic.plomyk.features.songs.list.presentation.SongBookViewModel

val songModule = module {

    // data
    factory { SongRepository(get()) }

    // presentation
    viewModel { SongBookViewModel(get()) }
}