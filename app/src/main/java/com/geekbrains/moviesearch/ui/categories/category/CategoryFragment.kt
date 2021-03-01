package com.geekbrains.moviesearch.ui.categories.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.vo.CategoryWithMovies
import com.geekbrains.moviesearch.ui.BaseMovieFragment
import com.geekbrains.moviesearch.ui.BaseViewModel
import com.geekbrains.moviesearch.ui.MoviesAdapter
import kotlinx.android.synthetic.main.app_bar_main.*


class CategoryFragment : BaseMovieFragment<CategoryWithMovies>() {

    override fun fragmentViewProvider(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getLong("categoryKey")?.also {
            (viewModel as CategoryViewModel).postSelectedCategoryId(it)
        }
    }

    override fun viewModel(): BaseViewModel<CategoryWithMovies> =
        ViewModelProvider(this).get(CategoryViewModel::class.java)

    override fun recyclerLayoutManagerProvider(): RecyclerView.LayoutManager =
        GridLayoutManager(context, 3)

    override fun toDetailsAction(): Int = R.id.action_nav_home_to_detailsFragment
    override fun showLoadedState(state: LoadingState<CategoryWithMovies>) {
        if (state is LoadingState.Success) {
            activity?.toolbar?.title = state.value.category.name
            (adapter as MoviesAdapter).setMovies(state.value.moviesInCategory)
        }
    }
}