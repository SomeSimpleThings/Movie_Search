package com.geekbrains.moviesearch.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.geekbrains.moviesearch.R

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }
}