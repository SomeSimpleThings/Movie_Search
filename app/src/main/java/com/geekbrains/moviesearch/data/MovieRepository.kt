package com.geekbrains.moviesearch.data

import com.geekbrains.moviesearch.api.Api
import com.geekbrains.moviesearch.data.vo.Category
import com.geekbrains.moviesearch.data.vo.Movie
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

interface Repository {

    fun getMovies(movieListFilter: MovieListFilter = MovieListFilter.All): List<Movie>
    fun getCategories(): List<Category>
    fun getMovieById(id: Int): Movie?
    fun getCategoryById(id: Int): Category

}

class RemoteRepositoryImpl : Repository {
    private val executorService: ExecutorService by lazy {
        Executors.newFixedThreadPool(3)
    }

    private val api: Api by lazy {
        Api()
    }

    override fun getMovies(movieListFilter: MovieListFilter): List<Movie> {
        return when (movieListFilter) {
            MovieListFilter.All -> {
                if (MovieListFilter.All.list.isEmpty()) {
                    val call = executorService.submit(Callable {
                        api.getDiscoverPage()?.movies ?: arrayListOf()
                    })
                    DummyContent.loadedMovies.addAll(call.get())
                }
                MovieListFilter.All.list
            }
            MovieListFilter.Favourites -> MovieListFilter.Favourites.list
            MovieListFilter.Watchlist -> MovieListFilter.Watchlist.list
        }
    }

    override fun getCategories(): List<Category> = DummyContent.REMOTE_CATEGORIES

    override fun getMovieById(id: Int): Movie? {
//        return MovieListFilter.All.list.firstOrNull { it.id == id }
        val call = executorService.submit(Callable {
            api.getMovieById(id)
        })
        return call.get()
    }

    override fun getCategoryById(id: Int): Category =
        DummyContent.getCategory(id)

    fun update(movie: Movie) = DummyContent.update(movie)

}

sealed class MovieListFilter {
    object All : MovieListFilter()
    object Favourites : MovieListFilter()
    object Watchlist : MovieListFilter()


    val list: List<Movie>
        get() = when (this) {
            is All -> DummyContent.loadedMovies
            is Favourites -> DummyContent.favouritesMovies
            is Watchlist -> DummyContent.watchMovies
        }
}