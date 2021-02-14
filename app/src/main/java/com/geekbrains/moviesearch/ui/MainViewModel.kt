package com.geekbrains.moviesearch.ui

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
    protected val repository: Repository = LocalRepositoryImpl()
) :
    ViewModel() {

    open fun getLoadedData() = run {
        responceLiveData.value = LoadingState.Loading
        loadingImitation()
        responceLiveData
    }

    open fun loadingImitation() {
        Thread {
            sleep(1000)
            responceLiveData.postValue(
                LoadingState.SuccessMovieLoad(
                    repository.getMovies(MovieListFilter.All)
                )
            )
        }.start()
    }

    fun updateMovie(movie: Movie) {
        (repository as LocalRepositoryImpl).let {
            repository.update(movie)
        }
        loadingImitation()
    }


}