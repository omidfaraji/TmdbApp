package com.faraji.challenge.tmdb

import androidx.preference.PreferenceManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.faraji.challenge.tmdb.network.di.*
import com.faraji.challenge.tmdb.network.service.TMDBApiService
import com.faraji.challenge.tmdb.ui.detail.MovieDetailViewModel
import com.faraji.challenge.tmdb.ui.home.MovieRepository
import com.faraji.challenge.tmdb.ui.home.MovieRepositoryImpl
import com.faraji.challenge.tmdb.ui.home.HomeViewModel
import com.faraji.challenge.tmdb.utils.EventBus
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appTestModule = module {
    factory { MovieRepositoryImpl(get()) }
    single { EventBus.instance }
    single { PreferenceManager.getDefaultSharedPreferences(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
}

val networkTestModule= module {
    single {
        MovieRepositoryImpl(get()) as MovieRepository
    }

    factory { get<Retrofit>(named(RETROFIT)).create(TMDBApiService::class.java) }


    single(named(READ_TIMEOUT)) { 30 * 1000 }
    single(named(WRITE_TIMEOUT)) { 10 * 1000 }
    single(named(CONNECTION_TIMEOUT)) { 10 * 1000 }

    factory(named(OK_HTTP_LOGGER)) {
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    single(named(OK_HTTP)) {
        OkHttpClient.Builder()
            .readTimeout(get(named(READ_TIMEOUT)), TimeUnit.MILLISECONDS)
            .writeTimeout(get(named(WRITE_TIMEOUT)), TimeUnit.MILLISECONDS)
            .connectTimeout(get(named(CONNECTION_TIMEOUT)), TimeUnit.MILLISECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>(named(OK_HTTP_LOGGER)))
            .build()
    }

    single(named(RETROFIT)) {
        Retrofit.Builder()
            .client(get(named(OK_HTTP)))
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}

