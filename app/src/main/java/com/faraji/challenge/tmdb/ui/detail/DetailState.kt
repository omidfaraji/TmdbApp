package com.faraji.challenge.tmdb.ui.detail

import com.faraji.challenge.tmdb.base.ViewState
import com.faraji.challenge.tmdb.network.model.movie.Movie

data class DetailState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val error: Throwable? = null
) : ViewState