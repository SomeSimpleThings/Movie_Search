package com.geekbrains.moviesearch.ui

import com.geekbrains.moviesearch.vo.Movie
import java.util.ArrayList
import java.util.HashMap


object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<Movie> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<Int, Movie> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i))
        }
    }

    private fun addItem(item: Movie) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createDummyItem(position: Int): Movie {
        return Movie(position, "Item " + position, "2020", "8.3")
    }
}