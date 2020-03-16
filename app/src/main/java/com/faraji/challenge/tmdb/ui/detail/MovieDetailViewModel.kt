package com.faraji.challenge.tmdb.ui.detail

import androidx.lifecycle.MutableLiveData
import com.faraji.challenge.tmdb.base.BaseViewModel
import com.faraji.challenge.tmdb.ui.home.MovieRepositoryImpl
import com.faraji.challenge.tmdb.utils.Coroutines
import kotlin.properties.Delegates

class MovieDetailViewModel(private val movieRepository: MovieRepositoryImpl) : BaseViewModel() {

    private val viewState = DetailState()
    var id by Delegates.notNull<Int>()
    val detailState = MutableLiveData(viewState)

    init {
        loadDetail()
    }

    private fun loadDetail() {
        Coroutines.ioThenMain(work = { movieRepository.getMovieDetail(id) }) {
            onExecute { detailState.value = viewState.copy(isLoading = true) }
            finally { detailState.value = viewState.copy(isLoading = false) }
            onComplete {
                detailState.value = viewState.copy(
                    isLoading = false,
                    movie = it,
                    error = null
                )
            }
            onError {
                detailState.value = viewState.copy(
                    isLoading = false,
                    error = it
                )
            }
        }.also { addToUnsubscribe(it) }
    }
}