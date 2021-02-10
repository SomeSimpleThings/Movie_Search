package com.geekbrains.moviesearch.data

import com.geekbrains.moviesearch.vo.Movie

interface Repository {

    fun getMovies(movieListFilter: MovieListFilter = MovieListFilter.All): List<Movie>

    fun getMovieById(id: Int?): Movie?

}

class LocalRepositoryImpl : Repository {
    override fun getMovies(movieListFilter: MovieListFilter): List<Movie> {
        return movieListFilter.list
    }

    override fun getMovieById(id: Int?): Movie? {
        return DummyContent.getById(id)
    }

    fun update(movie: Movie) {
        DummyContent.update()
    }


}

sealed class MovieListFilter {
    object All : MovieListFilter()
    object Favourites : MovieListFilter()
    object Watchlist : MovieListFilter()


    val list: List<Movie>
        get() {
            return when (this) {
                is All -> DummyContent.REMOTE_ITEMS
                is Favourites -> DummyContent.FAVOURITES_ITEMS
                is Watchlist -> DummyContent.WATCH_ITEMS
            }
        }
}