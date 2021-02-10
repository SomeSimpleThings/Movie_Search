package com.geekbrains.moviesearch.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.data.MovieListFilter
import com.geekbrains.moviesearch.model.MainViewModel
import com.geekbrains.moviesearch.ui.BaseRecyclerFragment
import com.geekbrains.moviesearch.vo.Movie


class HomeFragment : BaseRecyclerFragment() {


    override fun fragmentViewProvider(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, parent, false)

    }

    override fun viewModel(): MainViewModel =
        ViewModelProvider(this).get(MainViewModel::class.java)

    override fun recyclerItemLayoutId(): Int = R.layout.movie_cardview_item

    override fun recyclerLayoutManagerProvider(): RecyclerView.LayoutManager =
        GridLayoutManager(context, 3)

    override fun movieListFilter(): MovieListFilter = MovieListFilter.All

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onItemClicked(movie: Movie) {
        Bundle().let {
            it.putInt("movieKey", movie.id)
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_home_to_detailsFragment, it)
        }

    }
}