package com.geekbrains.moviesearch.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.moviesearch.MovieApplication
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.MovieRepository
import com.geekbrains.moviesearch.data.RepositoryImpl
import com.geekbrains.moviesearch.data.vo.Movie

class DetailsViewModel(
    private val movieRepository: MovieRepository = RepositoryImpl(
        MovieApplication.getMovieDao()
    ),
) : ViewModel() {

    fun getById(id: Int): MutableLiveData<LoadingState<Movie>> {
        return movieRepository.getMovieById(id)
    }

    fun updateMovie(movie: Movie) {
        (movieRepository as RepositoryImpl).let {
            movieRepository.updateMovie(movie)
        }
    }
}