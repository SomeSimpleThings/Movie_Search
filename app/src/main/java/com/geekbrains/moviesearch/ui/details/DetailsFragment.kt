package com.geekbrains.moviesearch.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.data.LoadingState
import com.geekbrains.moviesearch.data.remote.getImageUrl
import com.geekbrains.moviesearch.data.vo.Movie
import com.geekbrains.moviesearch.ui.getFavDravableResource
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment() {
    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }
    private var movie: Movie? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar_details.setNavigationOnClickListener { v ->
            v.findNavController().navigateUp()
        }
        arguments?.getInt("movieKey")?.let {
            viewModel.getById(it).observe(viewLifecycleOwner, {
                if (it is LoadingState.Success) {
                    movie = it.value
                    renderMovieInfo(it.value)
                }
            })
        }

        details_fab.setOnClickListener {
            movie?.let {
                it.favourite = !it.favourite
                viewModel.updateMovie(it)
            }
        }
    }

    private fun renderMovieInfo(movie: Movie) {
        with(movie) {
            fragment_toolbarLayout.title = title
            movie_year.text = releaseDate
            movie_rate.text = voteAverage.toString()
            movie_desc.text = overview
            details_fab.setImageResource(getFavDravableResource(favourite))
        }

        context?.let {
            movie.posterPath?.apply {
                Glide
                    .with(it)
                    .load(getImageUrl(this))
                    .centerCrop()
                    .placeholder(R.drawable.movie_card_foreground)
                    .into(main_backdrop)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }
}