package com.faraji.challenge.tmdb.network.model.movie

data class Movie(
    var backdropPath: String,
    val adult: Boolean,
    val overview: String,
    val releaseDate: String,
    val id: Int,
    val originalTitle: String,
    val originalLanguage: String,
    val title: String,
    val popularity: Float,
    val voteCount: Int,
    val video: Boolean,
    val vote_average: Float
)