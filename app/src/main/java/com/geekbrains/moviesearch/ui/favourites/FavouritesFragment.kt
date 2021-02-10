package com.geekbrains.moviesearch.ui.favourites

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
import com.geekbrains.moviesearch.model.FavouritesViewModel
import com.geekbrains.moviesearch.model.MainViewModel
import com.geekbrains.moviesearch.ui.BaseRecyclerFragment
import com.geekbrains.moviesearch.ui.OnItemClickListener
import com.geekbrains.moviesearch.vo.Movie


class FavouritesFragment : BaseRecyclerFragment(), OnItemClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun fragmentViewProvider(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favourites, parent, false)
    }

    override fun viewModel(): MainViewModel =
        ViewModelProvider(this).get(FavouritesViewModel::class.java)

    override fun recyclerItemLayoutId(): Int = R.layout.movie_cardview_item

    override fun recyclerLayoutManagerProvider(): RecyclerView.LayoutManager =
        GridLayoutManager(context, 3)

    override fun movieListFilter(): MovieListFilter = MovieListFilter.Favourites


    override fun onItemClicked(movie: Movie?) {
        super.onItemClicked(movie)
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_nav_favourites_to_detailsFragment)
    }
}