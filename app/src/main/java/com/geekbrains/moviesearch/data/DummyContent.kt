package com.geekbrains.moviesearch.data

import com.geekbrains.moviesearch.data.vo.Category
import com.geekbrains.moviesearch.data.vo.Movie


object DummyContent {

    val loadedMovies: MutableList<Movie> = ArrayList()
    val REMOTE_CATEGORIES: MutableList<Category> = ArrayList()
    var favouritesMovies: List<Movie> = loadedMovies.filter { it.favourite }
    var watchMovies: List<Movie> = loadedMovies.filter { it.inWatchList }

    fun update(movie: Movie) {
        favouritesMovies = loadedMovies.filter { it.favourite }
        watchMovies = loadedMovies.filter { it.inWatchList }
    }

    fun getCategory(id: Int): Category = REMOTE_CATEGORIES.first { it.id == id }
}

