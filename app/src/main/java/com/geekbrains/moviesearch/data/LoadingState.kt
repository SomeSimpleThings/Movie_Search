package com.geekbrains.moviesearch.data


sealed class LoadingState<out T : Any> {
    data class Success<out T : Any>(val value: T) : LoadingState<T>()
    object Loading : LoadingState<Nothing>()
    data class Error(val error: Throwable) : LoadingState<Nothing>()
}

