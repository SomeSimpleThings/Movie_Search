package com.geekbrains.moviesearch.data.remote

import com.geekbrains.moviesearch.BuildConfig

const val TMDB_API_URL = "https://api.themoviedb.org/3/"
const val TMDB_API_IMAGE_URL = "https://image.tmdb.org/t/p/w342"
const val API_KEY = BuildConfig.TMDB_API_KEY

enum class TrendingPeriod(period: String) {
    DAY("day"), WEEK("week")
}

fun getImageUrl(path: String) =
    "${TMDB_API_IMAGE_URL}$path"