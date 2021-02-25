package com.geekbrains.moviesearch.ui.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.MovieListFilter
import com.geekbrains.moviesearch.data.vo.Movie
import com.geekbrains.moviesearch.ui.BaseMovieFragment
import com.geekbrains.moviesearch.ui.BaseViewModel
import com.geekbrains.moviesearch.ui.MoviesAdapter
import com.geekbrains.moviesearch.ui.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_watchlist.*


class WatchlistFragment : BaseMovieFragment<List<Movie>>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemTouchHelper =
            ItemTouchHelper(SwipeToDeleteCallback(adapter as MoviesAdapter))
        itemTouchHelper.attachToRecyclerView(recycler_view as RecyclerView)
    }

    override fun fragmentViewProvider(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_watchlist, parent, false)

    override fun viewModel(): BaseViewModel<List<Movie>> =
        ViewModelProvider(this).get(WatchListViewModel::class.java)

    override fun recyclerItemLayoutId(): Int = R.layout.movie_list_item

    override fun recyclerLayoutManagerProvider(): RecyclerView.LayoutManager =
        LinearLayoutManager(context)

    override fun movieListFilter(): MovieListFilter = MovieListFilter.Watchlist

    override fun toDetailsAction(): Int = R.id.action_nav_watchlist_to_detailsFragment

    override fun showLoadedState(state: LoadingState<List<Movie>>) {
        if (state is LoadingState.Success)
            (adapter as MoviesAdapter).setMovies(state.value)
    }
}