package com.geekbrains.moviesearch.vo

import java.time.Year

data class Movie(val id: Int, val name: String, val year: String, val rating: String) {

}

object MovieCopier {
    fun copyMovie(movie: Movie) = movie.copy()
}