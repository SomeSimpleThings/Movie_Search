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
import com.geekbrains.moviesearch.ui.BaseMovieFragment
import com.geekbrains.moviesearch.ui.MainViewModel
import com.geekbrains.moviesearch.ui.MoviesAdapter
import kotlinx.android.synthetic.main.app_bar_main.*


class CategoryFragment : BaseMovieFragment() {

    override fun fragmentViewProvider(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("categoryKey")?.also {
            (viewModel as CategoryViewModel).postSelectedCategoryId(it)
        }
    }

    override fun showLoadedState(state: LoadingState) {
        if (state is LoadingState.SuccessCategoryLoad) {
            activity?.toolbar?.title = state.category.name
            (adapter as MoviesAdapter).setMovies(state.category.moviesInCategory)
        }
    }

    override fun viewModel(): MainViewModel =
        ViewModelProvider(this).get(CategoryViewModel::class.java)

    override fun recyclerLayoutManagerProvider(): RecyclerView.LayoutManager =
        GridLayoutManager(context, 3)

    override fun toDetailsAction(): Int = R.id.action_nav_home_to_detailsFragment
}