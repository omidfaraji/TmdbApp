package com.faraji.challenge.tmdb.network.model.movie

data class ListResponse<T>(
    val results: List<T>,
    val page: Int,
    val totalResults: Int,
    val totalPages: Int
) {
    companion object {
        fun <T> createEmpty() = ListResponse<T>(listOf(), -1, -1, -1)
    }
}