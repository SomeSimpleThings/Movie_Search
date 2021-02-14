package com.geekbrains.moviesearch.data

import com.geekbrains.moviesearch.vo.Category
import com.geekbrains.moviesearch.vo.Movie

sealed class LoadingState {
    data class SuccessMovieLoad(val movies: List<Movie>) : LoadingState()
    data class SuccessCategoriesLoad(val categories: List<Category>) : LoadingState()
    data class SuccessCategoryLoad(val category: Category) : LoadingState()
    data class Error(val error: Throwable) : LoadingState()
    object Loading : LoadingState()
}
