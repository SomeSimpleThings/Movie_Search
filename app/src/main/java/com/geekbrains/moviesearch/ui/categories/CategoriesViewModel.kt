package com.geekbrains.moviesearch.ui.categories

import androidx.lifecycle.MutableLiveData
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.vo.Category
import com.geekbrains.moviesearch.ui.BaseViewModel

class CategoriesViewModel(
) : BaseViewModel<List<Category>>() {

    override fun getLoadedData(): MutableLiveData<LoadingState<List<Category>>> {
        return movieRepository.getCategories()
    }
}