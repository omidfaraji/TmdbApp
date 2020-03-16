package com.faraji.challenge.tmdb.ui.home

import androidx.lifecycle.MutableLiveData
import com.faraji.challenge.tmdb.base.BaseViewModel
import com.faraji.challenge.tmdb.network.model.movie.ListResponse
import com.faraji.challenge.tmdb.network.model.movie.Movie
import com.faraji.challenge.tmdb.utils.Coroutines

class HomeViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {

    val homeState = MutableLiveData(HomeViewState())

    private var year: Int? = null
    private var page: Int = 1
    private var response = ListResponse.createEmpty<Movie>()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        if (response.totalPages != -1 && page >= response.totalPages)
            return

        Coroutines.ioThenMain(work = { movieRepository.getMovieList(page, year) }) {
            onExecute {
                homeState.value = homeState.value!!.copy(isLoading = true, year = getYear())
            }
            finally {
                homeState.value = homeState.value!!.copy(isLoading = false, year = getYear())
            }
            onComplete {
                response = it
                homeState.value = homeState.value!!.copy(
                    isLoading = false,
                    movies = it.results,
                    isFirstLoad = page <= 1,
                    year = getYear(),
                    error = null

                )
            }
            onError {
                homeState.value = homeState.value!!.copy(
                    isLoading = false,
                    year = getYear(),
                    error = it
                )
            }
        }.also { addToUnsubscribe(it) }
    }

    fun loadMore() {
        page++
        loadMovies()
    }

    fun filter(year: Int) {
        if (year == 1899)
            this.year = null
        else
            this.year = year
        page = 1
        loadMovies()
    }

    fun getYear() = year?.toString() ?: "All"
}