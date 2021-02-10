package com.geekbrains.moviesearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.model.MainViewModel
import com.geekbrains.moviesearch.vo.Movie
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_home.*

abstract class BaseRecyclerFragment : Fragment(), OnItemClickListener {

    protected lateinit var adapter: MovieRecyclerViewAdapter
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

    abstract fun recyclerAdapterProvider(): MovieRecyclerViewAdapter

    abstract fun recyclerLayoutManagerProvider(): RecyclerView.LayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        adapter = recyclerAdapterProvider()
        recycler_view?.let {
            it as RecyclerView
            it.layoutManager = recyclerLayoutManagerProvider()
            it.adapter = adapter
        }
        viewModel.getMovies().observe(viewLifecycleOwner, { showState(it) })

    }

    private fun showState(state: LoadingState) {
        val loadingLayout =
            activity?.findViewById<View>(R.id.mainFragmentLoadingLayout)
        when (state) {
            is LoadingState.Success -> {
                loadingLayout?.visibility = View.GONE
                adapter.setMovies(state.movies)
            }
            is LoadingState.Loading -> {
                loadingLayout?.visibility = View.VISIBLE
            }
            is LoadingState.Error -> {
                loadingLayout?.visibility = View.GONE
            }
        }
    }

    override fun onItemClicked(movie: Movie?) {
        viewModel.selectMovie(movie)
    }
}