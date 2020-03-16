package com.faraji.challenge.tmdb.network.service

import com.faraji.challenge.tmdb.BuildConfig
import com.faraji.challenge.tmdb.network.model.movie.ListResponse
import com.faraji.challenge.tmdb.network.model.movie.Movie
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApiService {

    @GET("/3/discover/movie")
    fun getMovies(
        @Query("page") page: Int,
        @Query("year") year: Int? = null,
        @Query("sort_by") sort: String = "popularity.desc",
        @Query("api_key") apiLey: String = BuildConfig.API_KEY
    ): Deferred<ListResponse<Movie>>

    @GET("/3/movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiLey: String = BuildConfig.API_KEY
    ): Deferred<Movie>


}