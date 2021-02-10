package com.geekbrains.moviesearch.model

import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.MovieListFilter

class WatchListViewModel : MainViewModel() {

    override fun loadingImitation() {
        Thread {
            responceLiveData.postValue(LoadingState.Success(repository.getMovies(MovieListFilter.Watchlist)))
        }.start()
    }
}