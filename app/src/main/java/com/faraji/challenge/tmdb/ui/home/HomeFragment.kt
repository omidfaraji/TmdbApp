package com.faraji.challenge.tmdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.faraji.challenge.tmdb.R
import com.faraji.challenge.tmdb.extentions.numberPicker
import com.faraji.challenge.tmdb.network.model.movie.Movie
import com.faraji.challenge.tmdb.utils.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel by viewModel<HomeViewModel>()

    private val scrollListener by lazy {
        object : EndlessRecyclerViewScrollListener(recyclerRepositories.layoutManager!!) {
            override fun onLoadMore() {
                viewModel.loadMore()
            }
        }
    }
    private val adapter by lazy {
        MovieListAdapter(context!!, onRepositoryClicked = {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToDetail(it.id)
            )
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.homeState.observe(viewLifecycleOwner, Observer { state ->
            txtYear.text = getString(R.string.year).plus(": ").plus(state.year)
            scrollListener.loading = state.isLoading
            state.movies.takeIf { it.isNotEmpty() }?.let {
                bindMovies(state.isFirstLoad, it)
            }
        })
        txtYear.setOnClickListener {
            activity!!.numberPicker {
                viewModel.filter(it)
            }
        }
        recyclerRepositories.apply {
            layoutManager = GridLayoutManager(this@HomeFragment.context!!, 2)
            adapter = this@HomeFragment.adapter
        }
        recyclerRepositories.addOnScrollListener(scrollListener)
    }

    private fun bindMovies(
        isFirstLoad: Boolean,
        movies: List<Movie>
    ) {
        if (isFirstLoad)
            adapter.items = movies.toMutableList()
        else
            adapter.appItems = movies
    }


}