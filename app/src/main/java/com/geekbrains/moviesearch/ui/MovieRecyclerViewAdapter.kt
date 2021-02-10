package com.geekbrains.moviesearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.vo.Movie

class MovieRecyclerViewAdapter(
    val layoutId: Int,
    val itemClickListener: OnItemClickListener,
    val favClickListener: OnFavClickListener,
    val watchClickListener: OnWatchClickListener
) : RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>() {

    private var values: List<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }


    override fun getItemCount(): Int = values.size
    fun deleteItem(adapterPosition: Int) {
        values.drop(adapterPosition)
        notifyItemRemoved(adapterPosition)
    }

    fun setMovies(movies: List<Movie>) {
        values = movies
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val yearView: TextView = view.findViewById(R.id.movie_year)
        val nameView: TextView = view.findViewById(R.id.movie_name)
        val rateView: TextView = view.findViewById(R.id.movie_rate)
        val favImageView: ImageView = view.findViewById(R.id.movie_fav_image)
        val watchImage: ImageView = view.findViewById(R.id.movie_watch_image)

        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }

        fun bind(item: Movie) = with(item) {
            yearView.text = year
            nameView.text = name
            rateView.text = rating
            favImageView.let { imageView ->
                imageView.setImageResource(getFavDravable(favourite))
                imageView.setOnClickListener {
                    favourite = !favourite
                    favClickListener.onFavClicked(this)
                }
            }

            watchImage.let { imageView ->
                imageView.setImageResource(getWatchlistDravable(inWatchList))
                imageView.setOnClickListener {
                    inWatchList = !inWatchList
                    watchClickListener.onWatchClicked(this)
                }
                itemView.setOnClickListener {
                    itemClickListener.onItemClicked(this)
                }
            }
        }
    }
}

fun getFavDravable(fav: Boolean) = when (fav) {
    true -> R.drawable.ic_baseline_favorite_24
    false -> R.drawable.ic_baseline_favorite_border_24
}

fun getWatchlistDravable(watch: Boolean) = when (watch) {
    true -> R.drawable.ic_baseline_playlist_add_check_24
    false -> R.drawable.ic_baseline_playlist_add_24
}


interface OnItemClickListener {
    fun onItemClicked(movie: Movie?)
}

interface OnFavClickListener {
    fun onFavClicked(movie: Movie)
}

interface OnWatchClickListener {
    fun onWatchClicked(movie: Movie)
}

interface OnItemRemovedListener {
    fun onItemRemoved(movie: Movie?)
}