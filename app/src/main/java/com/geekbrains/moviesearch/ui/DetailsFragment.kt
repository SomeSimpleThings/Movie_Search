package com.geekbrains.moviesearch.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.model.MainViewModel
import com.geekbrains.moviesearch.vo.Movie
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
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
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getById(key).observe(viewLifecycleOwner, {
            movie = it
            renderMovieInfo(it)
        })
        details_fab.setOnClickListener {
            movie?.let {
                it.favourite = !it.favourite
                viewModel.singleUpdateMovie(it)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }
}