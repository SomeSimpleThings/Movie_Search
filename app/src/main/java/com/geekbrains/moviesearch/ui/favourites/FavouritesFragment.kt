package com.geekbrains.moviesearch.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.data.MovieListFilter
import com.geekbrains.moviesearch.ui.BaseMovieFragment
import com.geekbrains.moviesearch.ui.MainViewModel


class FavouritesFragment : BaseMovieFragment() {

    override fun fragmentViewProvider(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favourites, parent, false)
    }

    override fun viewModel(): MainViewModel =
        ViewModelProvider(this).get(FavouritesViewModel::class.java)

    override fun recyclerLayoutManagerProvider(): RecyclerView.LayoutManager =
        GridLayoutManager(context, 3)

    override fun movieListFilter(): MovieListFilter = MovieListFilter.Favourites

    override fun toDetailsAction(): Int = R.id.action_nav_favourites_to_detailsFragment
}