package com.geekbrains.moviesearch.ui.categories

import androidx.lifecycle.MutableLiveData
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.ui.MainViewModel

class CategoriesViewModel(
) : MainViewModel() {
    override fun getLoadedData(): MutableLiveData<LoadingState> = run {
        responceLiveData.value = LoadingState.Loading
        loadingImitation()
        responceLiveData
    }

    override fun loadingImitation() {
        Thread {
            Thread.sleep(1000)
            responceLiveData.postValue(LoadingState.SuccessCategoriesLoad(repository.getCategories()))
        }.start()

    }
}