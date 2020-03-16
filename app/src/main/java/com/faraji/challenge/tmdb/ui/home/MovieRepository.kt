package com.faraji.challenge.tmdb.ui.home

import com.faraji.challenge.tmdb.network.model.movie.ListResponse
import com.faraji.challenge.tmdb.network.model.movie.Movie

interface MovieRepository {
    suspend fun getMovieList(page: Int, year: Int?): ListResponse<Movie>
    suspend fun getMovieDetail(id: Int):  Movie

}