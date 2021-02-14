package com.geekbrains.moviesearch.data

import com.geekbrains.moviesearch.vo.Category
import com.geekbrains.moviesearch.vo.Movie

interface Repository {

    fun getMovies(movieListFilter: MovieListFilter = MovieListFilter.All): List<Movie>
    fun getCategories(): List<Category>
    fun getMovieById(id: Int?): Movie?
    fun getCategoryById(id: Int): Category

}

class LocalRepositoryImpl : Repository {
    override fun getMovies(movieListFilter: MovieListFilter): List<Movie> = movieListFilter.list


    override fun getCategories(): List<Category> = DummyContent.REMOTE_CATEGORIES

    override fun getMovieById(id: Int?): Movie? = DummyContent.getById(id)

    override fun getCategoryById(id: Int): Category = DummyContent.getCategory(id)

    fun update(movie: Movie) = DummyContent.update()
}

sealed class MovieListFilter {
    object All : MovieListFilter()
    object Favourites : MovieListFilter()
    object Watchlist : MovieListFilter()


    val list: List<Movie>
        get() = when (this) {
            is All -> DummyContent.REMOTE_MOVIES
            is Favourites -> DummyContent.FAVOURITES_MOVIES
            is Watchlist -> DummyContent.WATCH_MOVIES
        }

}