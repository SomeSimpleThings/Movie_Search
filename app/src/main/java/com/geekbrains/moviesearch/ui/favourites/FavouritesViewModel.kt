package com.geekbrains.moviesearch.ui.favourites

import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.MovieListFilter
import com.geekbrains.moviesearch.ui.MainViewModel

class FavouritesViewModel : MainViewModel() {

    override fun loadingImitation() {
        Thread {
            responceLiveData.postValue(LoadingState.SuccessMovieLoad(repository.getMovies(MovieListFilter.Favourites)))
        }.start()
    }

}