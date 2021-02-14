package com.geekbrains.moviesearch.data

import com.geekbrains.moviesearch.vo.Category
import com.geekbrains.moviesearch.vo.Movie
import java.util.*


object DummyContent {

    val REMOTE_MOVIES: MutableList<Movie> = ArrayList()
    val REMOTE_CATEGORIES: MutableList<Category> = ArrayList()
    var FAVOURITES_MOVIES: List<Movie> = REMOTE_MOVIES.filter { it.favourite }
    var WATCH_MOVIES: List<Movie> = REMOTE_MOVIES.filter { it.inWatchList }
    private const val MOVIE_COUNT = 25
    private const val CATEGORY_COUNT = 5

    init {
        for (i in 1..MOVIE_COUNT) {
            addMovieToRemote(createDummyMovieItem(i))
        }

        for (i in 1..CATEGORY_COUNT) {
            addCategoryToRemote(createDummyCategoryItem(i))
        }
    }

    private fun addMovieToRemote(item: Movie) = REMOTE_MOVIES.add(item)
    private fun addCategoryToRemote(item: Category) = REMOTE_CATEGORIES.add(item)

    private fun createDummyMovieItem(position: Int): Movie =
        Movie(position, movieNames.random(), years.random(), ratings.random(), largeDesc)

    private fun createDummyCategoryItem(position: Int): Category = Category(
        position,
        categories.random(),
        REMOTE_MOVIES.filter { it.id >= position && it.id <= position * 5 })

    fun getById(id: Int?): Movie? = REMOTE_MOVIES.firstOrNull { it.id == id }

    fun update() {
        FAVOURITES_MOVIES = REMOTE_MOVIES.filter { it.favourite }
        WATCH_MOVIES = REMOTE_MOVIES.filter { it.inWatchList }
    }

    fun getCategory(id: Int): Category = REMOTE_CATEGORIES.first { it.id == id }
}

val categories = listOf("Hot", "New", "Popular", "Recommended")

val movieNames =
    listOf(
        "Bladerunner 2049",
        "Matrix",
        "Borat: the movie",
        "Harry potter and chamber of secrets",
        "Appolo 11"
    )
val years = listOf("1985", "2020", "1993", "2005", "2011")
val ratings = listOf("6.3", "7.1", "8.5", "8.9")

val largeDesc = "Blade Runner 2049 is a 2017 American neo-noir science fiction film directed by" +
        "Denis Villeneuve and written by Hampton Fancher and Michael Green. A sequel" +
        "to the 1982 film Blade Runner, the film stars Ryan Gosling and Harrison Ford," +
        "with Ana de Armas, Sylvia Hoeks, Robin Wright, Mackenzie Davis, Carla Juri," +
        "Lennie James, Dave Bautista, and Jared Leto in supporting roles. Ford and Edward" +
        "James Olmos reprise their roles from the original. Gosling plays K, a Nexus-9" +
        "replicant \"blade runner\" who uncovers a secret that threatens to destabilize" +
        "society and the course of civilization." +
        "Ideas for a Blade Runner sequel were first proposed in the 1990s," +
        "but licensing issues stalled their development. Andrew Kosove and Broderick Johnson" +
        "obtained the film rights from Bud Yorkin. Ridley Scott stepped down as the film" +
        "initial director and worked as an executive producer, while Villeneuve was later" +
        "appointed to direct. Blade Runner 2049 was financed through an Alcon Entertainment–Sony" +
        "Pictures partnership and a Hungarian government-funded tax rebate. Warner Bros.," +
        "on behalf of Alcon, distributed the film in North America, while Sony handled distribution" +
        "in international markets. Principal photography took place mostly at two soundstages in" +
        "Budapest over a four-month period from July to November 2016." +
        "Blade Runner 2049 premiered in Los Angeles on October 3, 2017, and was released in" +
        "the United States in 2D, 3D, and IMAX on October 6, 2017. The film received acclaim" +
        "from critics, who praised its performances, direction, cinematography, editing," +
        "musical score, production design, visual effects, and faithfulness to the original" +
        "film, and was widely considered to be among the best films of 2017. However," +
        "it was a box office disappointment, grossing $260.5 million worldwide against a" +
        "production budget between $150–185 million.[9][11][12] Blade Runner 2049 was nominated" +
        "for and won several accolades, receiving five nominations at the 90th Academy Awards," +
        "winning Best Cinematography and Best Visual Effects. It also received eight nominations" +
        "at the 71st British Academy Film Awards, including Best Director, and won Best" +
        "Cinematography and Best Special Visual Effects."

