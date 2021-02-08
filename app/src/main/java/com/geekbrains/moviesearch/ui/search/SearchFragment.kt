package com.geekbrains.moviesearch.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.ui.DummyContent
import com.geekbrains.moviesearch.ui.MovieRecyclerViewAdapter

/**
 * A fragment representing a list of Items.
 */
class SearchFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_list, container, false)

        view.findViewById<RecyclerView>(R.id.recycler_view)?.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = MovieRecyclerViewAdapter(DummyContent.ITEMS, R.layout.movie_list_item)
        }

        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}