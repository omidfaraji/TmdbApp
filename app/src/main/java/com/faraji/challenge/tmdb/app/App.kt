package com.faraji.challenge.tmdb.app

import android.app.Application
import androidx.preference.PreferenceManager
import com.faraji.challenge.tmdb.network.di.accessTokenModule
import com.faraji.challenge.tmdb.network.di.networkModule
import com.faraji.challenge.tmdb.ui.detail.MovieDetailViewModel
import com.faraji.challenge.tmdb.ui.home.HomeViewModel
import com.faraji.challenge.tmdb.ui.home.MovieRepositoryImpl
import com.faraji.challenge.tmdb.utils.EventBus
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val APPLICATION_CONTEXT = "APPLICATION_CONTEXT"

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, networkModule, accessTokenModule))
        }
    }

    private val appModule = module {
        factory { MovieRepositoryImpl(get()) }
        single { EventBus.instance }
        single(named(APPLICATION_CONTEXT)) { applicationContext }
        single { PreferenceManager.getDefaultSharedPreferences(get()) }
        viewModel { HomeViewModel(get()) }
        viewModel { MovieDetailViewModel(get()) }
    }

}