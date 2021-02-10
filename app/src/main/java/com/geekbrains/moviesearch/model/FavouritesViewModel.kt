package com.geekbrains.moviesearch.model

import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.MovieListFilter

class FavouritesViewModel : MainViewModel() {

    override fun loadingImitation() {
        Thread {
            responceLiveData.postValue(LoadingState.Success(repository.getMovies(MovieListFilter.Favourites)))
        }.start()
    }

}