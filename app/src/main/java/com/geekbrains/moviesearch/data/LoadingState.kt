package com.geekbrains.moviesearch.data

import com.geekbrains.moviesearch.vo.Movie

sealed class LoadingState {
    data class Success(val movies: List<Movie>) : LoadingState()
    data class Error(val error: Throwable) : LoadingState()
    object Loading : LoadingState()
}
