package com.geekbrains.moviesearch.data

import com.geekbrains.moviesearch.vo.Movie
import java.util.ArrayList


object DummyContent {

    val ITEMS: MutableList<Movie> = ArrayList()
    private const val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i))
        }
    }

    private fun addItem(item: Movie) {
        ITEMS.add(item)
    }

    private fun createDummyItem(position: Int): Movie {
        return Movie(position, movieNames.random(), years.random(), ratings.random())
    }

    fun getById(id: Int): Movie? = ITEMS.firstOrNull { it.id == id }
}

val movieNames =
    listOf("Bladerunner 2049", "Matrix", "Borat: the movie", "Harry potter and chamber of secrets")
val years = listOf("1985", "2020", "1993", "2005", "2011")
val ratings = listOf("6.3", "7.1", "8.5", "8.9")

