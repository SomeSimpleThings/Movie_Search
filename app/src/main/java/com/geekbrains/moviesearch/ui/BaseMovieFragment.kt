package com.geekbrains.moviesearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.MovieListFilter
import com.geekbrains.moviesearch.vo.Movie
import kotlinx.android.synthetic.main.fragment_home.*

abstract class BaseMovieFragment : Fragment(), OnMovieItemClickListener {

    protected lateinit var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    protected lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return fragmentViewProvider(inflater, container, savedInstanceState)
    }

    abstract fun fragmentViewProvider(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View

    abstract fun viewModel(): ViewModel
    abstract fun recyclerLayoutManagerProvider(): RecyclerView.LayoutManager

    open fun movieListFilter(): MovieListFilter = MovieListFilter.All
    open fun recyclerItemLayoutId(): Int = R.layout.movie_cardview_item


    open fun recyclerAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> =
        MovieRecyclerViewAdapter(
            recyclerItemLayoutId(),
            this,
        ) as RecyclerView.Adapter<RecyclerView.ViewHolder>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = viewModel() as MainViewModel
        adapter = recyclerAdapter()
        recycler_view?.let {
            it as RecyclerView
            it.layoutManager = recyclerLayoutManagerProvider()
            it.adapter = adapter
        }
        viewModel.getLoadedData().observe(viewLifecycleOwner, { showState(it) })

    }

    open fun showState(state: LoadingState) {
        val loadingLayout =
            activity?.findViewById<View>(R.id.mainFragmentLoadingLayout)
        when (state) {
            is LoadingState.SuccessMovieLoad -> {
                loadingLayout?.visibility = View.GONE
                (adapter as MovieRecyclerViewAdapter).setMovies(state.movies)
            }
            is LoadingState.Loading -> {
                loadingLayout?.visibility = View.VISIBLE
            }
            is LoadingState.Error -> {
                loadingLayout?.visibility = View.GONE
            }
        }
    }

    override fun onMovieIconsClicked(movie: Movie) {
        viewModel.updateMovie(movie)
    }
}