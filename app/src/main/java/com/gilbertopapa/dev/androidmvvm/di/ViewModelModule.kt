package com.gilbertopapa.dev.androidmvvm.di

import com.ardyyy.dev.androidmvvm.presentation.detail.DetailViewModel
import com.ardyyy.dev.androidmvvm.presentation.favorite.FavoriteViewModel
import com.ardyyy.dev.androidmvvm.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel( get()) }
    viewModel { DetailViewModel( get(), get()) }
    viewModel { FavoriteViewModel( get()) }
}