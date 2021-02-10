package com.geekbrains.moviesearch.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.LocalRepositoryImpl
import com.geekbrains.moviesearch.data.MovieListFilter
import com.geekbrains.moviesearch.data.Repository
import com.geekbrains.moviesearch.vo.Movie
import java.lang.Thread.sleep

open class MainViewModel(
    protected val responceLiveData: MutableLiveData<LoadingState> = MutableLiveData(),
    protected val selectedMovieLiveData: MutableLiveData<Movie> = MutableLiveData(),
    protected val repository: Repository = LocalRepositoryImpl()
) :
    ViewModel() {

    fun getMovies() = run {
        responceLiveData.value = LoadingState.Loading
        loadingImitation()
        responceLiveData
    }

    open fun loadingImitation() {
        Thread {
            sleep(1000)
            responceLiveData.postValue(LoadingState.Success(repository.getMovies(MovieListFilter.All)))
        }.start()
    }

    fun selectMovie(movie: Movie?) {
        selectedMovieLiveData.postValue(movie)
    }

    fun getSelectedMovie() = selectedMovieLiveData

    fun processFavClick(movie: Movie) {
        repository.getMovies(MovieListFilter.Favourites).let {
            when (movie.favourite) {
                true -> it.add(movie)
                else -> it.remove(movie)
            }
        }
        loadingImitation()
    }

    fun processWatchClick(movie: Movie) {
        repository.getMovies(MovieListFilter.Watchlist).let {
            when (movie.inWatchList) {
                true -> it.add(movie)
                false -> it.remove(movie)
            }
        }
        loadingImitation()
    }
}