package com.geekbrains.moviesearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.api.Api
import com.geekbrains.moviesearch.data.vo.Movie


class MoviesAdapter(
    private val layoutId: Int,
    private val itemClickListener: OnMovieItemClickListener,
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

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
        val posterImage: ImageView = view.findViewById(R.id.movie_image)

        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }

        fun bind(item: Movie) = with(item) {
            yearView.text = releaseDateYear
            nameView.text = title
            rateView.text = voteAverage.toString()
            posterPath?.let {
                Glide
                    .with(itemView.getContext())
                    .load(Api.getImageUrl(posterPath))
                    .centerCrop()
                    .placeholder(R.drawable.movie_card_foreground)
                    .into(posterImage)
            }
            favImageView.setImageResource(getFavDravableResource(favourite))
            favImageView.setOnClickListener {
                favourite = !favourite
                itemClickListener.onMovieIconsClicked(this)
            }

            watchImage.setImageResource(getWatchlistDravableResource(inWatchList))
            watchImage.setOnClickListener {
                inWatchList = !inWatchList
                itemClickListener.onMovieIconsClicked(this)
            }
            itemView.setOnClickListener {
                itemClickListener.onMovieClicked(this)
            }
        }

    }
}


interface OnMovieItemClickListener {
    fun onMovieClicked(movie: Movie)
    fun onMovieIconsClicked(movie: Movie)
}

interface OnItemRemovedListener {
    fun onItemRemoved(movie: Movie?)
}