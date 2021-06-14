package szymon.swic.plomyk.core.di

import androidx.room.Room
import androidx.room.Room.databaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import szymon.swic.plomyk.core.database.AppDatabase

private const val DATABASE_NAME = "db"

val databaseModule = module {
    single {
        databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    single { get<AppDatabase>().songDao() }
}