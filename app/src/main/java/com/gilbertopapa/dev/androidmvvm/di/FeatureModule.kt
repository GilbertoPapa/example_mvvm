package com.gilbertopapa.dev.androidmvvm.di

import com.ardyyy.dev.androidmvvm.data.repository.LocalRepository
import com.ardyyy.dev.androidmvvm.data.repository.MovieRepository
import org.koin.dsl.module

val featureModule = module {
    single { MovieRepository(get()) }
    single { LocalRepository(get()) }
}