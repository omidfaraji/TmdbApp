package com.faraji.challenge.tmdb.ui.home

import com.faraji.challenge.tmdb.base.ViewState
import com.faraji.challenge.tmdb.network.model.movie.Movie

data class HomeViewState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = listOf(),
    val isFirstLoad: Boolean = true,
    val year: String = "All",
    val error: Throwable? = null
) : ViewState