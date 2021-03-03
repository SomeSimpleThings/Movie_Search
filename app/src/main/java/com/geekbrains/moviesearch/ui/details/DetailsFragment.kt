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
import com.geekbrains.moviesearch.databinding.FragmentDetailsBinding
import com.geekbrains.moviesearch.ui.getFavDravableResource


class DetailsFragment : Fragment() {
    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }
    private var movie: Movie? = null

    private lateinit var binding: FragmentDetailsBinding

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
        binding = FragmentDetailsBinding.bind(view)
        binding.toolbarDetails.setNavigationOnClickListener { v ->
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

        binding.detailsFab.setOnClickListener {
            movie?.let {
                it.favourite = !it.favourite
                viewModel.updateMovie(it)
            }
        }
    }

    private fun renderMovieInfo(movie: Movie) {
        with(movie) {
            binding.fragmentToolbarLayout.title = title
            binding.movieYear.text = releaseDate
            binding.movieRate.text = voteAverage.toString()
            binding.movieDesc.text = overview
            binding.detailsFab.setImageResource(getFavDravableResource(favourite))
        }

        context?.let {
            movie.posterPath?.apply {
                Glide
                    .with(it)
                    .load(getImageUrl(this))
                    .centerCrop()
                    .placeholder(R.drawable.movie_card_foreground)
                    .into(binding.mainBackdrop)
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