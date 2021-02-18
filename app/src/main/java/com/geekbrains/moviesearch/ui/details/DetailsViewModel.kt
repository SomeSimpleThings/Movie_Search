package com.geekbrains.moviesearch.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.moviesearch.data.RemoteRepositoryImpl
import com.geekbrains.moviesearch.data.Repository
import com.geekbrains.moviesearch.data.vo.Movie

class DetailsViewModel(
    private val repository: Repository = RemoteRepositoryImpl(),
    private val selectedMovieLiveData: MutableLiveData<Movie> = MutableLiveData(),
) : ViewModel() {

    fun getById(id: Int): MutableLiveData<Movie> {
        selectedMovieLiveData.postValue(repository.getMovieById(id))
        return selectedMovieLiveData
    }

    fun updateMovie(movie: Movie) {
        (repository as RemoteRepositoryImpl).let {
            repository.update(movie)
            selectedMovieLiveData.postValue(repository.getMovieById(movie.id))
        }
    }
}