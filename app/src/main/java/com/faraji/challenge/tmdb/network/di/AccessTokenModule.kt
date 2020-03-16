package com.faraji.challenge.tmdb.network.di

import com.faraji.challenge.tmdb.network.service.TMDBApiService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val accessTokenModule = module {
    factory { get<Retrofit>(named(RETROFIT)).create(TMDBApiService::class.java) }
}