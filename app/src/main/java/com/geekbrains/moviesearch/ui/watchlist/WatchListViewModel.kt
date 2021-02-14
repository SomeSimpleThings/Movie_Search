package com.geekbrains.moviesearch.ui.watchlist

import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.MovieListFilter
import com.geekbrains.moviesearch.ui.MainViewModel

class WatchListViewModel : MainViewModel() {

    override fun loadingImitation() {
        Thread {
            responceLiveData.postValue(LoadingState.SuccessMovieLoad(repository.getMovies(MovieListFilter.Watchlist)))
        }.start()
    }
}