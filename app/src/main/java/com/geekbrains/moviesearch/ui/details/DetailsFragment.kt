package com.geekbrains.moviesearch.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.ui.getFavDravableResource
import com.geekbrains.moviesearch.vo.Movie
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
        val key = arguments?.getInt("movieKey")
        viewModel.getById(key).observe(viewLifecycleOwner, {
            movie = it
            renderMovieInfo(it)
        })
        details_fab.setOnClickListener {
            movie?.let {
                it.favourite = !it.favourite
                viewModel.updateMovie(it)
            }
        }
    }

    private fun renderMovieInfo(movie: Movie) {
        fragment_toolbarLayout.title = movie.name
        movie_year.text = movie.year
        movie_rate.text = movie.rating
        movie_desc.text = movie.description
        details_fab.setImageResource(getFavDravableResource(movie.favourite))
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