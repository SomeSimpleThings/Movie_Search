package com.geekbrains.moviesearch.ui.categories.category

import androidx.lifecycle.MutableLiveData
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.RepositoryImpl
import com.geekbrains.moviesearch.data.vo.Category
import com.geekbrains.moviesearch.ui.BaseViewModel

class CategoryViewModel(
    private var currentCategoryId: Int = -1
) : BaseViewModel<Category>() {

    override fun getLoadedData(): MutableLiveData<LoadingState<Category>> {
        return movieRepository.getCategoryById(currentCategoryId)
    }

    fun postSelectedCategoryId(id: Int) {
        (movieRepository as RepositoryImpl).let {
            movieRepository.updateCategory(id)
        }
    }
}


