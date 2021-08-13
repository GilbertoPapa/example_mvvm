package com.gilbertopapa.dev.androidmvvm

import androidx.multidex.MultiDexApplication
import com.ardyyy.dev.androidmvvm.di.apiModule
import com.ardyyy.dev.androidmvvm.di.dbModule
import com.ardyyy.dev.androidmvvm.di.featureModule
import com.ardyyy.dev.androidmvvm.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : MultiDexApplication() {
    private val modules = listOf(
        apiModule,
        featureModule,
        dbModule,
        viewModelModule
    )

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(modules)
        }
    }
}