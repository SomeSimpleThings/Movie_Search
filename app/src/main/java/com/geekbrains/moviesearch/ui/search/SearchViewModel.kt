package com.geekbrains.moviesearch.ui.search

import androidx.lifecycle.MutableLiveData
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.MovieListFilter
import com.geekbrains.moviesearch.data.vo.Movie
import com.geekbrains.moviesearch.ui.BaseViewModel

class SearchViewModel : BaseViewModel<List<Movie>>() {
    override fun getLoadedData(showAdult: Boolean): MutableLiveData<LoadingState<List<Movie>>> {
        return movieRepository.getMovies(MovieListFilter.All,showAdult)
    }
}