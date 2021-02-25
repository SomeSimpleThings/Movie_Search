package com.geekbrains.moviesearch.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.MovieListFilter
import com.geekbrains.moviesearch.data.vo.Movie
import com.geekbrains.moviesearch.ui.BaseMovieFragment
import com.geekbrains.moviesearch.ui.BaseViewModel
import com.geekbrains.moviesearch.ui.MoviesAdapter


class FavouritesFragment : BaseMovieFragment<List<Movie>>() {

    override fun fragmentViewProvider(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favourites, parent, false)
    }

    override fun viewModel(): BaseViewModel<List<Movie>> =
        ViewModelProvider(this).get(FavouritesViewModel::class.java)

    override fun recyclerLayoutManagerProvider(): RecyclerView.LayoutManager =
        GridLayoutManager(context, 3)

    override fun movieListFilter(): MovieListFilter = MovieListFilter.Favourites

    override fun toDetailsAction(): Int = R.id.action_nav_favourites_to_detailsFragment
    override fun showLoadedState(state: LoadingState<List<Movie>>) {
        if (state is LoadingState.Success)
            (adapter as MoviesAdapter).setMovies(state.value)
    }
}