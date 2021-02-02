package com.geekbrains.moviesearch.vo

data class Movie(val id: Int, val name: String) {
    override fun toString(): String = "$id $name"
}

object MovieCopier {
    fun copyMovie(movie: Movie) = movie.copy()
}