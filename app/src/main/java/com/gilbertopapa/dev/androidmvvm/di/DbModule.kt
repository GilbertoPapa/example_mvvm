package com.gilbertopapa.dev.androidmvvm.di

import com.gilbertopapa.dev.androidmvvm.data.local.room.MovieAppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {

    single { com.gilbertopapa.dev.androidmvvm.data.local.room.MovieAppDatabase.getDatabase(androidContext()) }

    single {
        val movieDatabase: com.gilbertopapa.dev.androidmvvm.data.local.room.MovieAppDatabase = get()
        return@single movieDatabase.movieDao()
    }
}