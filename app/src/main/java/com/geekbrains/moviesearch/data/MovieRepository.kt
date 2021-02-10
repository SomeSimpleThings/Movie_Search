package com.geekbrains.moviesearch.data

import com.geekbrains.moviesearch.vo.Movie

interface Repository {

    fun getMovies(): MutableList<Movie>

    fun getMovieById(id: Int): Movie?
}

class LocalRepositoryImpl : Repository {
    override fun getMovies(): MutableList<Movie> {
        return DummyContent.ITEMS
    }

    override fun getMovieById(id: Int): Movie? {
        return DummyContent.getById(id)
    }
}