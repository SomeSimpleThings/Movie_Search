package com.geekbrains.moviesearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.MovieListFilter
import com.geekbrains.moviesearch.data.vo.Movie
import kotlinx.android.synthetic.main.fragment_home.*

abstract class BaseMovieFragment : Fragment(), OnMovieItemClickListener {

    protected val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder> by lazy {
        recyclerAdapter()
    }
    protected val viewModel: MainViewModel by lazy {
        viewModel() as MainViewModel
    }

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
    abstract fun toDetailsAction(): Int


    open fun movieListFilter(): MovieListFilter = MovieListFilter.All
    open fun recyclerItemLayoutId(): Int = R.layout.movie_cardview_item

    open fun recyclerAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> =
        MoviesAdapter(
            recyclerItemLayoutId(),
            this,
        ) as RecyclerView.Adapter<RecyclerView.ViewHolder>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view?.let {
            it as RecyclerView
            it.layoutManager = recyclerLayoutManagerProvider()
            it.adapter = adapter
        }
        viewModel.getLoadedData().observe(viewLifecycleOwner, { state ->
            activity?.findViewById<View>(R.id.mainFragmentLoadingLayout)?.showIf {
                state is LoadingState.Loading
            }
            showLoadedState(state)
        })
    }

    override fun onMovieClicked(movie: Movie) {
        Bundle().also {
            it.putInt("movieKey", movie.id)
            NavHostFragment.findNavController(this)
                .navigate(toDetailsAction(), it)
        }
    }

    open fun showLoadedState(state: LoadingState) {
        if (state is LoadingState.SuccessMovieLoad)
            (adapter as MoviesAdapter).setMovies(state.movies)
    }

    override fun onMovieIconsClicked(movie: Movie) {
        viewModel.updateMovie(movie)
    }
}