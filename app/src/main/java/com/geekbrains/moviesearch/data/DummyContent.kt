package com.geekbrains.moviesearch.data

import com.geekbrains.moviesearch.data.vo.Category
import com.geekbrains.moviesearch.data.vo.Movie


object DummyContent {

    val loadedMovies: MutableList<Movie> = ArrayList()
    val REMOTE_CATEGORIES: MutableList<Category> = ArrayList()
    var favouritesMovies: List<Movie> = loadedMovies.filter { it.favourite }
    var watchMovies: List<Movie> = loadedMovies.filter { it.inWatchList }
    private const val CATEGORY_COUNT = 5

    init {
        repeat(CATEGORY_COUNT) {
            addCategoryToRemote(createDummyCategoryItem(it))
        }
    }

    private fun addCategoryToRemote(item: Category) = REMOTE_CATEGORIES.add(item)

    private fun createDummyCategoryItem(position: Int): Category = Category(
        position,
        categories.random(),
        ArrayList()
    )

    fun update(movie: Movie) {
        favouritesMovies = loadedMovies.filter { it.favourite }
        watchMovies = loadedMovies.filter { it.inWatchList }
    }

    fun getCategory(id: Int): Category = REMOTE_CATEGORIES.first { it.id == id }
}

val categories = listOf("Hot", "New", "Popular", "Recommended")

