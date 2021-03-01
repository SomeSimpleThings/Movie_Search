package com.geekbrains.moviesearch.ui.categories

import androidx.lifecycle.MutableLiveData
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.vo.Category
import com.geekbrains.moviesearch.data.vo.CategoryWithMovies
import com.geekbrains.moviesearch.ui.BaseViewModel

class CategoriesViewModel(
) : BaseViewModel<List<CategoryWithMovies>>() {

    override fun getLoadedData(showAdult: Boolean): MutableLiveData<LoadingState<List<CategoryWithMovies>>> {
        return movieRepository.getCategories()
    }
}