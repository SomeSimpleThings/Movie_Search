package com.geekbrains.moviesearch.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.LocalRepositoryImpl
import com.geekbrains.moviesearch.data.Repository
import com.geekbrains.moviesearch.vo.Movie
import java.lang.Thread.sleep

class MainViewModel(
    private val responceLiveData: MutableLiveData<LoadingState> = MutableLiveData(),
    private val selectedMovieLiveData: MutableLiveData<Movie> = MutableLiveData(),
    private val repository: Repository = LocalRepositoryImpl()
) :
    ViewModel() {

    fun getMovies() = run {
        responceLiveData.value = LoadingState.Loading
        loadingImitation()
        responceLiveData
    }

    private fun loadingImitation() {
        Thread {
            sleep(1000)
            responceLiveData.postValue(LoadingState.Success(repository.getMovies()))
        }.start()
    }

    fun selectMovie(movie: Movie?) {
        selectedMovieLiveData.postValue(movie)
    }

    fun getSelectedMovie() = selectedMovieLiveData
}