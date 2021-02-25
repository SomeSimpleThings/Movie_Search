package com.geekbrains.moviesearch.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.MovieRepository
import com.geekbrains.moviesearch.data.RepositoryImpl
import com.geekbrains.moviesearch.data.vo.Movie

abstract class BaseViewModel<T : Any>(
    protected var responseLiveData: MutableLiveData<LoadingState<T>> = MutableLiveData(),
    protected val movieRepository: MovieRepository = RepositoryImpl()
) :
    ViewModel() {

    abstract fun getLoadedData(): MutableLiveData<LoadingState<T>>
    fun updateMovie(movie: Movie) {
        (movieRepository as RepositoryImpl).let {
            movieRepository.updateMovie(movie)
        }
    }
}