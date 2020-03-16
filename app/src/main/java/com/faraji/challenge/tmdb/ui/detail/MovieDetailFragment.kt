package com.faraji.challenge.tmdb.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.faraji.challenge.tmdb.R
import com.faraji.challenge.tmdb.extentions.load
import com.faraji.challenge.tmdb.network.model.movie.Movie
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment() {

    private val viewModel by viewModel<MovieDetailViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.id = MovieDetailFragmentArgs.fromBundle(arguments!!).id

        viewModel.detailState.observe(viewLifecycleOwner, Observer { state ->
            state.movie?.let {
                bindMovie(it)
            }
        })

    }

    private fun bindMovie(movie: Movie) {
        imgMoviePoster.load(movie.backdropPath)
        txtMovieTitle.text = movie.title
        txtMovieOverview.text = movie.overview
    }


}