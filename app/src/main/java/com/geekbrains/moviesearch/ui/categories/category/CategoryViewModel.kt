package com.geekbrains.moviesearch.ui.categories.category

import androidx.lifecycle.MutableLiveData
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.RepositoryImpl
import com.geekbrains.moviesearch.data.vo.CategoryWithMovies
import com.geekbrains.moviesearch.ui.BaseViewModel

class CategoryViewModel(
    private var currentCategoryId: Long = -1
) : BaseViewModel<CategoryWithMovies>() {

    override fun getLoadedData(showAdult: Boolean): MutableLiveData<LoadingState<CategoryWithMovies>> {
        return movieRepository.getCategoryById(currentCategoryId)
    }

    fun postSelectedCategoryId(id: Long) {
        (movieRepository as RepositoryImpl).let {
            movieRepository.updateCategory(id)
        }
    }
}


