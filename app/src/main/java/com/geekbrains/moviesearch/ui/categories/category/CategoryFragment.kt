package com.geekbrains.moviesearch.ui.categories.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.MovieListFilter
import com.geekbrains.moviesearch.ui.BaseMovieFragment
import com.geekbrains.moviesearch.ui.MainViewModel
import com.geekbrains.moviesearch.ui.MovieRecyclerViewAdapter
import com.geekbrains.moviesearch.vo.Movie
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
        val key = arguments?.getInt("categoryKey")
        (viewModel as CategoryViewModel).postSelectedCategoryId(key)

    }

    override fun showState(state: LoadingState) {
        val loadingLayout =
            activity?.findViewById<View>(R.id.mainFragmentLoadingLayout)
        when (state) {
            is LoadingState.SuccessCategoryLoad -> {
                loadingLayout?.visibility = View.GONE
                activity?.toolbar?.title = state.category.name
                (adapter as MovieRecyclerViewAdapter).setMovies(state.category.moviesInCategory)
            }
            is LoadingState.Loading -> {
                loadingLayout?.visibility = View.VISIBLE
            }
            else -> {
                loadingLayout?.visibility = View.GONE
            }
        }
    }

    override fun viewModel(): MainViewModel =
        ViewModelProvider(this).get(CategoryViewModel::class.java)

    override fun recyclerLayoutManagerProvider(): RecyclerView.LayoutManager =
        GridLayoutManager(context, 3)

    override fun movieListFilter(): MovieListFilter = MovieListFilter.All

    override fun onMovieClicked(movie: Movie) {
        Bundle().let {
            it.putInt("movieKey", movie.id)
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_home_to_detailsFragment, it)
        }

    }
}