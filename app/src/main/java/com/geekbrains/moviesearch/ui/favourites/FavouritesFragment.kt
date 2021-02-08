package com.geekbrains.moviesearch.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.moviesearch.R
import com.geekbrains.moviesearch.ui.DummyContent
import com.geekbrains.moviesearch.ui.MovieRecyclerViewAdapter
import com.geekbrains.moviesearch.ui.OnItemClickListener
import com.geekbrains.moviesearch.ui.SwipeToDeleteCallback
import com.geekbrains.moviesearch.vo.Movie

/**
 * A fragment representing a list of Items.
 */
class FavouritesFragment : Fragment(), OnItemClickListener {

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
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)

        // Set the adapter
        view.findViewById<RecyclerView>(R.id.recycler_view)?.let {
            it.layoutManager = GridLayoutManager(context, 3)
            val adapter =
                MovieRecyclerViewAdapter(
                    DummyContent.ITEMS,
                    R.layout.movie_cardview_item,
                    this
                )
            it.adapter = adapter
            val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
            itemTouchHelper.attachToRecyclerView(it)
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            FavouritesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    override fun onItemClicked(responce: Movie?) {
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_nav_favourites_to_detailsFragment)

    }
}