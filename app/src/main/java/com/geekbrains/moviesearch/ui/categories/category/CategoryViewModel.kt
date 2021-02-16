package com.geekbrains.moviesearch.ui.categories.category

import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.ui.MainViewModel

class CategoryViewModel(
    private var currentCategoryId: Int = -1
) : MainViewModel() {

    override fun loadingImitation() {
        Thread {
            responceLiveData.postValue(
                when (currentCategoryId) {
                    -1 -> LoadingState.Loading
                    else -> {
                        LoadingState.SuccessCategoryLoad(
                            repository.getCategoryById(currentCategoryId)
                        )
                    }
                }
            )
        }.start()
    }

    fun postSelectedCategoryId(id: Int) {
        currentCategoryId = id
        loadingImitation()
    }
}