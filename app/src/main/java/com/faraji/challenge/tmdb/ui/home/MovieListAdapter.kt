package com.faraji.challenge.tmdb.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faraji.challenge.tmdb.R
import com.faraji.challenge.tmdb.extentions.load
import com.faraji.challenge.tmdb.network.model.movie.Movie
import kotlinx.android.synthetic.main.item_movie_list.view.*


class MovieListAdapter(
    val context: Context,
    items: MutableList<Movie> = mutableListOf(),
    val onRepositoryClicked: (Movie) -> Unit

) : RecyclerView.Adapter<MovieListAdapter.RepositoryHolder>(), View.OnClickListener {

    var items: MutableList<Movie> = items
        set(value) {
            if (value == field)
                return
            field = value
            notifyDataSetChanged()
        }
    var appItems: List<Movie> = items
        set(value) {
            if (value == field)
                return
            field = value
            val startIndex = items.size
            items.addAll(value)
            notifyItemRangeInserted(startIndex, value.size)
        }


    inner class RepositoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener(this@MovieListAdapter)
        }

        fun bind(movie: Movie, position: Int) {
            itemView.txtMovieTitle.text = movie.title
            itemView.imgMoviePoster.load(movie.backdropPath)
            itemView.tag = position
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {

        return RepositoryHolder(parent.inflater(R.layout.item_movie_list))
    }

    private fun ViewGroup.inflater(layoutId: Int): View {
        return LayoutInflater.from(context).inflate(
            layoutId,
            this,
            false
        )
    }

    override fun getItemCount() = items.size

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position)
    }

    override fun onClick(v: View?) {
        val position = v!!.tag.toString().toInt()
        val item = items[position]
        onRepositoryClicked(item)
    }


}