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
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getSelectedMovie().observe(viewLifecycleOwner, {
            renderMovieInfo(it)
        })
    }

    private fun renderMovieInfo(movie: Movie) {
        fragment_toolbarLayout.title = movie.name
        movie_year.text = movie.year
        movie_rate.text = movie.rating
        movie_desc.text = movie.description
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