package com.gilbertopapa.dev.androidmvvm.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val apiModule = module {
    single { createWebService(androidContext()) }
}