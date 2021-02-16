package com.geekbrains.moviesearch.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.MovieListFilter
import com.geekbrains.moviesearch.data.RemoteRepositoryImpl
import com.geekbrains.moviesearch.data.Repository
import com.geekbrains.moviesearch.data.vo.Movie

open class MainViewModel(
    protected val responceLiveData: MutableLiveData<LoadingState> = MutableLiveData(),
    protected val repository: Repository = RemoteRepositoryImpl()
) :
    ViewModel() {

    open fun getLoadedData() = run {
        responceLiveData.value = LoadingState.Loading
        loadingImitation()
        responceLiveData
    }

    open fun loadingImitation() {
        Thread {
            responceLiveData.postValue(
                LoadingState.SuccessMovieLoad(
                    repository.getMovies(MovieListFilter.All)
                )
            )
        }.start()
    }

    fun updateMovie(movie: Movie) {
        (repository as RemoteRepositoryImpl).let {
            repository.update(movie)
        }
        loadingImitation()
    }
}