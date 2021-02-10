package com.geekbrains.moviesearch.data

import com.geekbrains.moviesearch.vo.Movie

interface Repository {

    fun getMovies(movieListFilter: MovieListFilter = MovieListFilter.All): MutableList<Movie>

    fun getMovieById(id: Int): Movie?
}

class LocalRepositoryImpl : Repository {
    override fun getMovies(movieListFilter: MovieListFilter): MutableList<Movie> {
        return movieListFilter.list
    }

    override fun getMovieById(id: Int): Movie? {
        return DummyContent.getById(id)
    }

    fun addToFavourites(movie: Movie) {
        DummyContent.FAVOURITES_ITEMS.add(movie)
    }

    fun removeFromFavourites(movie: Movie) {
        DummyContent.FAVOURITES_ITEMS.remove(movie)
    }

    fun addToWatchList(movie: Movie) {
        DummyContent.WATCH_ITEMS.add(movie)
    }

    fun removeFromWatchlist(movie: Movie) {
        DummyContent.WATCH_ITEMS.remove(movie)
    }
}

sealed class MovieListFilter {
    object All : MovieListFilter()
    object Favourites : MovieListFilter()
    object Watchlist : MovieListFilter()


    val list: MutableList<Movie>
        get() {
            return when (this) {
                is All -> DummyContent.REMOTE_ITEMS
                is Favourites -> DummyContent.FAVOURITES_ITEMS
                is Watchlist -> DummyContent.WATCH_ITEMS
            }
        }
}