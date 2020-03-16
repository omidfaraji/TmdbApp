package com.faraji.challenge.tmdb.ui.home

import com.faraji.challenge.tmdb.network.service.TMDBApiService

const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

class MovieRepositoryImpl(private val TMDBApiService: TMDBApiService) : MovieRepository {

    override suspend fun getMovieList(page: Int, year: Int?) =
        TMDBApiService.getMovies(page, year).await().apply {
            results.forEach {
                it.backdropPath = IMAGE_BASE_URL.plus("w300").plus(it.backdropPath)
            }
        }

    override suspend fun getMovieDetail(id: Int) =
        TMDBApiService.getMovieDetail(id).await().apply {
            backdropPath = IMAGE_BASE_URL.plus("w780").plus(backdropPath)
        }

}