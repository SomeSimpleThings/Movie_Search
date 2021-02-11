package com.geekbrains.moviesearch.vo

data class Movie(
    val id: Int,
    val name: String,
    val year: String,
    val rating: String,
    val description: String?,
    var favourite: Boolean = false,
    var inWatchList: Boolean = false
) {

}

object MovieCopier {
    fun copyMovie(movie: Movie) = movie.copy()
}